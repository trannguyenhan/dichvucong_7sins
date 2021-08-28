
    const url = "http://localhost:3000/thanh";
    var  headers = {
        'Origin': 'http://localhost:3000',
        'Accept': '*/*',
        'Accept-Encoding': 'gzip, deflate, br',
        'Accept-Language': 'en-US,en;q=0.9',
        'User-Agent': 'Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/90.0.4430.93 Safari/537.36'
    }
    
    fetch(url, {
        method : "GET",
        mode: 'no-cors',
        headers : headers
    })
    .then(rs => console.log(rs));