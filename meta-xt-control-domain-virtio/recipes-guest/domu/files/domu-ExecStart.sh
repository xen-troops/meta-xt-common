#!/bin/bash

DOMU_ID=""

create_domain() {
    echo "Creating DomU"
    /usr/sbin/xl create /etc/xen/domu.cfg
    echo "Created DomU"

    # Fetch DomU Xen domain identifier
    while [ "$DOMU_ID" == "" ]; do
        DOMU_ID=$(xl list | awk '{ if ($1 == "DomU") print $2 }')

        if [ "$DOMU_ID" = "" ]; then
            sleep 1
        else
            echo "Parsed DOMU_ID is '${DOMU_ID}'"
        fi
    done
}

clean_up_domain() {
    if [ "$DOMU_ID" != "" ]; then
        echo "Cleaning up DomU domain with id ${DOMU_ID}"

        # Here we re-fetch domain id by intention.
        # If there is no domain id available, probably the user of the system
        # has called "xl destroy" explicitly, so there is no need to call
        # it the second time.
        local DOMU_ID_LOCAL=$(xl list | awk '{ if ($1 == "DomU") print $2 }')

        local XL_DESTROY_PID=""

        if [ "$DOMU_ID_LOCAL" != "" ]; then
            echo "Destroying DomU"
            /usr/sbin/xl destroy DomU &
            XL_DESTROY_PID=$!
            echo "Destroyed DomU"
        else
            echo "Was not able to fetch DomU domain id. Skip destroying the domain."
        fi

        echo "Updating virtio statuses in the xen-store."

        for i in `xenstore-ls /libxl/${DOMU_ID}/device/virtio | grep 'backend =' | awk '{print $3}'`
        do
            i=${i%\"}
            i=${i#\"}
            xenstore-write $i/state "6"
            xenstore-write $i/online "0"
        done

        echo "Updated virtio statuses in the xen-store."

        if [ "$XL_DESTROY_PID" != "" ]; then
            echo "Waiting for 'xl destroy' to release."
            wait ${XL_DESTROY_PID}
            echo "'xl destroy' has released."
        fi

        echo "Cleaned up DomU domain with id ${DOMU_ID}"
    else
        echo "No id of DomU available. Skip destroying and cleaning up the domain."
    fi
}

monitor_domain() {
    echo "DomU restart monitor started!"

    local DOMD_ID=$(xl list | awk '{ if ($1 == "DomD") print $2 }')
    echo "Parsed DOMD_ID is '${DOMD_ID}'"

    # We assume that at least 1 qemu virtio backend is always available.
    XS_PATH="/local/domain/$DOMD_ID/backend/qvirtio/${DOMU_ID}/0/state"
    local QVIRTIO_STATUS="unknown"

    while true; do
        if [ "$QVIRTIO_STATUS" = "unknown" ]; then
            xenstore-watch -n1 $XS_PATH > /dev/null
        else
            xenstore-watch -n2 $XS_PATH > /dev/null
        fi

        local QVIRTIO_NEW_STATUS=`xenstore-read $XS_PATH || true`

        echo "'QVIRTIO_NEW_STATUS status is '$QVIRTIO_NEW_STATUS'."
        if [ "$QVIRTIO_NEW_STATUS" != "$QVIRTIO_STATUS" ]; then
            if [ "$QVIRTIO_NEW_STATUS" = "1" ]; then
                echo "Domain 'DomU' has become available."
            elif [ "$QVIRTIO_NEW_STATUS" = "6" -a "$QVIRTIO_STATUS" != "unknown" ]; then
                echo "Domain 'DomU' with id '${DOMU_ID}' has become unavailable."
                clean_up_domain
                echo "Exiting with status 1."
                exit 1
            fi
        fi

        QVIRTIO_STATUS=$QVIRTIO_NEW_STATUS
    done
}

terminate() {
    echo "Received SIGTERM signal..."
    clean_up_domain
    echo "Exiting with status 0."
    exit 0
}

trap 'terminate' SIGTERM

create_domain
monitor_domain
