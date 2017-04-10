##本案例在 CentOS7 core, redis.3.2.6 中完成
## 解压redis.tar.gz
tar -xvf redis.tar.gz
## 安装GCC
yum -y install gcc
## 编译redis 
cd redis/
make MALLOC=libc
## 新建config 文件夹
mkdir config
## 进入config 文件夹
cd config/
## 新建 cluster-run 
vi cluster-run
## 输入 i 进入编辑模式
## 文本内容粘贴
## 按下esc 退出编辑模式
## 输入 :wq! 保存退出
## 将cluster-run权限修改为755 使其用执行(x)权限
chmod 755 cluster-run 
## 或 将cluster-run 文件上传至 config 然后修改权限
## 执行cluster-run文件
./cluster-sun start
##  查看开启状态
ps -ef|grep redis

## 将3台redis服务器开启后,执行集群部署
## redis集群注意事项
 #1, redis.config文件 安全模式(protected-mode no)关闭,否者无法实现多服务部署redis集群
 
 #2, 需要安装gem ,gem redis (仅开启部署端需要) 注意版本(本案例在redis.3.2.6完成) 
 
 #3, gem 的安装注意镜像源,部分源可以使用迅雷下载本地后部署,修改/etc/hosts文件 完成资源加载超时问题
 
 #4, redis-sentinel 只需监听一个master,其他会自动关联

## 开放端口
firewall-cmd --zone=public --add-port=6379-6382/tcp --permanent

firewall-cmd --zone=public --add-port=16379-16382/tcp --permanent

firewall-cmd --zone=public --add-port=6379-6382/udp --permanent

firewall-cmd --zone=public --add-port=16379-16382/udp --permanent

firewall-cmd --reload

firewall-cmd --zone=public --list-port

## 集群开去至少6个节点3个master节点 3个slave节点,(因redis集群管理方式为选举,所以至少3个,当一个master挂掉后,至少有2个投票,选出新的master)
## 开启集群
./redis-trib.rb create --replicas 3 192.168.16.72:6379 192.168.16.62:6379 192.168.16.65:6379 192.168.16.65:6380 192.168.16.65:6381 192.168.16.65:6382 192.168.16.72:6380 192.168.16.72:6381 192.168.16.72:6382 192.168.16.62:6380 192.168.16.62:6381 192.168.16.62:6382

## 清除 防火墙单独开发的端口
firewall-cmd --zone=public --remove-port=6379/tcp --permanent

firewall-cmd --zone=public --remove-port=6380/tcp --permanent

firewall-cmd --zone=public --remove-port=6381/tcp --permanent

firewall-cmd --zone=public --remove-port=6381/udp --permanent

firewall-cmd --zone=public --remove-port=6380/udp --permanent

firewall-cmd --zone=public --remove-port=6379/udp --permanent

firewall-cmd --reload

firewall-cmd --zone=public --list-port



## 集群配置：3台机器，每台8个实例，redis版本3.3.2，ruby版本2.3.1，gem版本2.5.1
## 在执行./redis-trib.rb  create --replicas 3语句时，已显示配置好的主从节点，当我输入yes之后就会报以下错误：
 #>Can I set the above configuration? (type 'yes' to accept): yes /usr/local/lib/ruby/gems/2.3.0/gems/redis-3.3.2/lib/redis/connection/ruby.rb:111:in `rescue in _write_to_socket': Connection timed out (Redis::TimeoutError)
##解决办法 降低 gem redis 版本
gem list

gem uninstall redis --version 3.3.2

gem install redis --version 3.0.0

gem list



## 创建集群时报某个err slot 0 is already busy (redis::commanderror)这是由于之间创建集群没有成功,需要将nodes.conf和dir里面的文件全部删除(注意不要删除了redis.conf)
./cluster-run clean


## redis集群资料链接
 #1, http://doc.redisfans.com/topic/cluster-tutorial.html#id2
## redis-sentinel 资料链接
 #1, http://doc.redisfans.com/topic/sentinel.html#id4
