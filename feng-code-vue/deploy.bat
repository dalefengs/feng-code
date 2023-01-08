ssh root@lzscxb.cn "rm -rf /www/wwwroot/code.lzscxb.cn/*"
scp -r .\dist\* root@lzscxb.cn:/www/wwwroot/code.lzscxb.cn
pause