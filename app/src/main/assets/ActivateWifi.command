adb_port="5555"
msgTitle="ActiveTool (https://dysquard.github.io/pgpa/)"

DIR="$( cd "$( dirname "${BASH_SOURCE[0]}" )" && pwd )"

$DIR/adb kill-server
$DIR/adb devices
$DIR/adb disconnect


BTN_RETURNED=""

function detect(){

    if [ `$DIR/adb devices|grep "unauthorized"|wc -l` -ge 1 ]
    then phoneUnauthorized
         return
    fi

    $DIR/adb root
    if [ $? -eq 1 ]
    then phoneNotFound
         return
    fi

    if [ $? -eq 0 ]
    then inject
         return
    fi
}


phoneIP=""

function inject(){
    tryWifiConnect
    $DIR/adb -s $phoneIP:$adb_port shell mkdir /data/local/tmp/2
    $DIR/adb -s $phoneIP:$adb_port push $DIR/scripts/* /data/local/tmp/2/
    $DIR/adb -s $phoneIP:$adb_port shell sh /data/local/tmp/2/inject2.sh
    osascript -e 'display dialog "Activation completed, please launch APP Panda Gamepad Pro or Panda Mouse Pro on your phone and check if activated. If activation fails after unplugging, please run ActivateWifi.command" buttons {"OK"} default button 1'
    exit

}

function tryWifiConnect(){
    $DIR/adb disconnect >nul
    phoneIP=`$DIR/adb shell ip addr|grep "wlan0"|grep "inet"`
    phoneIP=`echo ${phoneIP%/*}|cut -c 6-50`
    ping -c 3 $phoneIP

    if [ $? != 0 ]
    then titleStr="Failed, please ensure WIFI on your phone is connected and in the same LAN(local network) with your PC."
         btnReturned "$titleStr" tryWifiConnect
    fi

    $DIR/adb tcpip $adb_port
    $DIR/adb connect $phoneIP:$adb_port

    return

}

function phoneNotFound(){


    titleStr="Phone not found \n1. Make sure your phone's USB Debugging is on; \n2. Reconnect your phone to PC via USB cable;\n3. Click Retry."

    btnReturned "$titleStr" detect

    return
}

function phoneUnauthorized(){

    titleStr="Tap OK button on your phones popup window and click retry. \n \n If popup not shown, click Cancel and run me again."

    btnReturned "$titleStr" detect

    return
}

function btnReturned(){

    BTN_RETURNED=$(osascript <<APPLESCRIPT

    set titleStr to "$1"
    set btns to {"Quit", "Retry"}
    set btnReturned to the button returned of (display dialog titleStr buttons btns default button 2)
    return btnReturned

    APPLESCRIPT
                )

    if [ $2 -a $BTN_RETURNED = "Retry" ]
    then $2
    elif [ $BTN_RETURNED = "Quit" ]
    then exit
    else return
    fi

}

detect
