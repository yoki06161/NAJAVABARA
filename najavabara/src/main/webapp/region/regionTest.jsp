<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>GeoLocation api test</title>
<!-- 위치 정보가 변경될 때마다 위치정보를 얻어오는 예제 (watchPosition)
[출처] [HTML5] 위치 정보 (Geolocation API)|작성자 창랑 -->
<script>
function getLocationFromIP() {
    var apiUrl = 'https://api.ipgeolocation.io/ipgeo?apiKey=b29ffee780fc45d0890e6de5338d3bce';
    
    fetch(apiUrl)
    .then(response => response.json())
    .then(data => {
        // 지역명과 국가명을 번역하여 출력
        Promise.all([translateToKorean(data.city)])
            .then(results => {
                var koreanRegion = results[0];
                document.getElementById('location').innerHTML = koreanRegion;
            })
            .catch(error => {
                console.error('번역 중 오류 발생:', error);
            });
    })
    .catch(error => {
        console.error('지역 정보를 가져오는 중 오류 발생:', error);
    });
}

//지역명을 한국어로 번역하는 함수
function translateToKorean(text) {
    var apiKey = 'AIzaSyCyLNQqK8w7xtQp1S3Coii3bjn6K49i3iE';
    var apiUrl = 'https://translation.googleapis.com/language/translate/v2?key=' + apiKey;
    var data = {
            q: text,
            source: 'en',
            target: 'ko'
        };

        return fetch(apiUrl, {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
            },
            body: JSON.stringify(data)
        })
        .then(response => response.json())
        .then(result => {
            return result.data.translations[0].translatedText;
        })
        .catch(error => {
            console.error('번역 중 오류 발생:', error);
            return text;
        });
}

// 페이지가 로드되면 getLocationFromIP 함수 호출
window.onload = getLocationFromIP;
</script>
</head>
<body>
	<h1>사용자의 위치 정보</h1>
	<p id="location" name="location"></p>
</body>
</html>