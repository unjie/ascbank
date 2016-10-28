::获取日期 将格式设置为：20110820
set datevar=%date:~0,4%%date:~5,2%%date:~8,2%
::获取时间中的小时 将格式设置为：24小时制
set timevar=%time:~0,2%
if /i %timevar% LSS 10 (
set timevar=0%time:~1,1%
)
::获取时间中的分、秒 将格式设置为：3220 ，表示 32分20秒
set timevar=%timevar%%time:~3,2%%time:~6,2%
@echo 当前时间：%datevar%%timevar%
#pg_dump dbname > outfile
pg_dump -U root ascbank > ascbank-pgDB_%datevar%%timevar%.sql
root123