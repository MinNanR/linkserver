export default function download(url, params, name, type) {
    //获取参数
    let data = '';
    for (const key in params) {
        data += `${key}=${params[key]}&`
    }
    data = data.substring(0, data.length - 1)
    url = url + '?' + data;
    //  获取时间戳
    let timestamp = new Date().getTime();
    // 获取XMLHttpRequest
    let xmlResquest = new XMLHttpRequest();
    //  发起请求
    let baseURl = process.env.NODE_ENV === 'development' ? '/api' : window.location.protocol + '//' + window.location.host
    xmlResquest.open("GET", baseURl + url, true);
    // 设置请求头类型
    xmlResquest.setRequestHeader("Content-type", "application/json");
    //  设置请求token
    xmlResquest.setRequestHeader(
        "token",
        window.sessionStorage.getItem('token')
    );
    xmlResquest.responseType = "blob";
    //  返回
    xmlResquest.onload = function (oEvent) {
        let res = xmlResquest.response;

        const blob = new Blob([res], {
            type: "application/vnd.ms-excel"
        })
        //   const fileName = `${title}${type.toUpperCase() === 'CSV' ? '.csv' : '.xls'}`
        let temp = xmlResquest.getResponseHeader("content-disposition").split(";")[1].split("filename=")[1]

        const fileName = decodeURIComponent(temp)
        if (window.navigator.msSaveOrOpenBlob) {
            navigator.msSaveBlob(blob, fileName)
        } else {
            const link = document.createElement('a')
            link.style = 'display:none'
            document.body.appendChild(link)
            link.href = window.URL.createObjectURL(blob)
            link.download = fileName
            link.click()
            window.URL.revokeObjectURL(link.href)
            document.body.removeChild(link)
        }
    };
    xmlResquest.send();
}