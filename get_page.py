import requests
import re
import os
def get_page(url:str,filename:str="temp.html"):
    request_head = {
        'User-Agent': 'Mozilla/5.0 (Windows NT 6.3; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/41.0.2272.118 Safari/537.36'}


    file = requests.get(url,headers=request_head)
    with open(filename,'w+') as f:
        f.write(str(file.content))

get_page("https://www.baidu.com")