import urllib2
import simplejson as json

OUTPUT = './data/'

def getip():
    return urllib2.urlopen('http://ip.42.pl/raw').read()

def get_faroo_news(q, start=1, length=10):
    url = 'http://www.faroo.com/api?l=en&src=news&f=json&q=%s&start=%d&length=%d' % (q, start, length)

def get_google_news(q):
    data = []
    base = 'https://ajax.googleapis.com/ajax/services/search/news?v=1.0&rsz=8&q=%s&userip=%s' % (q, getip())
    for i in range(0, 57, 8):
        url = base + '&start=' + str(i)
        req = urllib2.Request(url, None, {'Referer': getip()})
        resp = urllib2.urlopen(req)
        results = json.load(resp)
        results = results['responseData']['results']
        results = [{'title':j['title'],'url':j['url'],'content':j['content']} for j in results]
        data = data + results
    return data

def main():
    topic = 'Rutgers'
    data = get_google_news(topic)
    output = OUTPUT + topic + '.json'
    with open(output, 'w') as f:
        f.write(json.dumps(data))

if __name__ == '__main__':
    main()
