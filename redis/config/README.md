## 开放端口
firewall-cmd --zone=public --add-port=6379-6382/tcp --permanent

firewall-cmd --zone=public --add-port=16379-16382/tcp --permanent

firewall-cmd --zone=public --add-port=6379-6382/udp --permanent

firewall-cmd --zone=public --add-port=16379-16382/udp --permanent

firewall-cmd --reload

firewall-cmd --zone=public --list-port


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
## Can I set the above configuration? (type 'yes' to accept): yes /usr/local/lib/ruby/gems/2.3.0/gems/redis-3.3.2/lib/redis/connection/ruby.rb:111:in `rescue in _write_to_socket': Connection timed out (Redis::TimeoutError)
## 降低 gem redis 版本
gem list

gem uninstall redis --version 3.3.2

gem install redis --version 3.0.0

gem list



## 创建集群时报某个err slot 0 is already busy (redis::commanderror)这是由于之间创建集群没有成功,需要将nodes.conf和dir里面的文件全部删除(注意不要删除了redis.conf)