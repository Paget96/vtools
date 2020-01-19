#!/system/bin/sh

action=$1
if [[ "$action" = "init" ]] && [[ -f '/data/powercfg-base.sh' ]]; then
    sh /data/powercfg-base.sh
	exit 0
fi

function set_online() {
    echo 1 > /sys/devices/system/cpu/cpu0/online
    echo 1 > /sys/devices/system/cpu/cpu1/online
    echo 1 > /sys/devices/system/cpu/cpu2/online
    echo 1 > /sys/devices/system/cpu/cpu3/online
}


function set_freq() {
    local freq_min="$1"
    local freq_max="$2"
    set_online

    echo interactive > /sys/devices/system/cpu/cpu0/cpufreq/scaling_governor
    echo $freq_min > /sys/devices/system/cpu/cpu0/cpufreq/scaling_min_freq
    echo $freq_max > /sys/devices/system/cpu/cpu0/cpufreq/scaling_max_freq

    echo interactive > /sys/devices/system/cpu/cpu1/cpufreq/scaling_governor
    echo $freq_min > /sys/devices/system/cpu/cpu1/cpufreq/scaling_min_freq
    echo $freq_max > /sys/devices/system/cpu/cpu1/cpufreq/scaling_max_freq

    echo interactive > /sys/devices/system/cpu/cpu2/cpufreq/scaling_governor
    echo $freq_min > /sys/devices/system/cpu/cpu2/cpufreq/scaling_min_freq
    echo $freq_max > /sys/devices/system/cpu/cpu2/cpufreq/scaling_max_freq

    echo interactive > /sys/devices/system/cpu/cpu3/cpufreq/scaling_governor
    echo $freq_min > /sys/devices/system/cpu/cpu3/cpufreq/scaling_min_freq
    echo $freq_max > /sys/devices/system/cpu/cpu3/cpufreq/scaling_max_freq
}

# if [[ -f /system/bin/changepowermode.sh ]]; then
#     /system/bin/changepowermode.sh middle
# fi

# if [[ -f /system/bin/changepowermode.sh ]]; then
#     /system/bin/changepowermode.sh high
# else

if [[ "$action" = "powersave" ]]; then
    start mpdecision
    set_freq 300000 1958400

	exit 0
elif [[ "$action" = "balance" ]]; then
    stop mpdecision
    set_freq 300000 2457600

	exit 0
elif [[ "$action" = "performance" ]]; then
    stop mpdecision
    set_freq 960000 2457600

	exit 0
elif [[ "$action" = "fast" ]]; then
    stop mpdecision
    set_freq 1574400 2457600

	exit 0
fi
