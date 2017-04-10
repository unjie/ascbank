## 硬盘查看
/sbin/fdisk -l
## 系统复制
## /dev/sda 是源系统硬盘 /dev/sdb 是新硬盘
dd if=/dev/sda of=/dev/sdb