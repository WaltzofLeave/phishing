import re
import urllib.parse
RED_KEYWORDS = ["account", "admin", "administrator",
"auth", "bank", "client", "confirm", "email", "host",
"password", "pay", "private", "safe", "secure", "security",
"sign", "user", "validation", "verification", "icbc"]

PATH_KEYWORDS = ["www", "net", "com", "cn"]
def path_exist_hex(url):
    old_result = urllib.parse.urlparse(url.strip())
    path = old_result.path
    pat = re.compile(r'[0x0000-0xFFFF]+')
    path_list = re.findall(pat,path)
    if len(path_list) != 0:
        return 1
    else:
        return 0


def geturlat(url):
    """检查有无[@-_?~]"""
    re_s = re.compile(r'@|-|_|\?|~')
    return 1 if re_s.search(url) else 0


def geturldot(url):
    """url中‘.’的个数"""
    dotnum = 0
    for u in url:
        if u == '.':
            dotnum += 1
    return dotnum


def get_url_length(url):
    "url的长度"
    return len(url)

def get_url_number_length(url):
    """
    url中最长串的长度
    :param url:
    :return:
    """
    result = 0
    match = re.findall(r'\d+',url)
    if match:
        match.sort(key=lambda x:len(x),reverse=True)
        result = len(match[0])
    return result


def get_red_key_words(url):
    """判断url中是否存在敏感词汇（登录验证信息，安全信息，银行名等等）"""
    url = url.lower()
    for key in RED_KEYWORDS:
        if url.find(key) != -1:
            return 1
    return 0

def get_path_key_words(url:str):
    """判断url路径中是否存在不应当存在的词汇"""
    new_result_dict = urllib.parse.urlparse(url.strip())
    path = new_result_dict.path
    if path:
        for key in PATH_KEYWORDS:
            if path.lower().find(key) != -1:
                return 1
            return 0
    else:
        return 0

t = path_exist_hex("https://www.baidu.com/s?rsv_idx=1&wd=web%E5%89%8D%E7%AB%AF%E6%98%AF%E4%BB%80%E4%B9%88&fenlei=256&usm=1&ie=utf-8&rsf=162660005&rsv_dl=0_prs_28608_4&rsv_pq=a1fdb28b0003acd")
print(t)