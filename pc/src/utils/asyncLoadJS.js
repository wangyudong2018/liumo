import $ from 'jQuery'

export default function asyncLoadJs (url) {
  return new Promise((resolve, reject) => {
    let hasLoaded = $('script[src="' + url + '"]').length > 0
    if (hasLoaded) {
      resolve()
      return
    }
    let script = document.createElement('script')
    script.type = 'text/javascript'
    script.src = url
    document.body.appendChild(script)
    script.onload = () => {
      resolve()
    }
    script.onerror = (err) => {
      reject(err)
    }
  })
}

export function loadAMapJS () {
  return new Promise((resolve, reject) => {
    asyncLoadJs('http://api.map.baidu.com/api?v=3.0&ak=DTvpddNZXw3opkwd9bt7sd9RZLash8yk&callback=initialize')
      .then(() => {
        resolve()
      })
      .catch(err => {
        reject(err)
      })
  })
}
