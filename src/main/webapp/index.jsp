<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Devcolibri.com exam REST</title>
</head>

<script src="//ajax.googleapis.com/ajax/libs/jquery/1.10.2/jquery.min.js"></script>
<script type="text/javascript">
    var prefix = '/onlineshop-rest/myservice';

    var RestGet = function () {
        $.ajax({
            type: 'GET',
            url: prefix + '/' + Date.now(),
            dataType: 'json',
            async: true,
            success: function (result) {
                alert('Время: ' + result.time
                        + ', сообщение: ' + result.message);
            },
            error: function (jqXHR, textStatus, errorThrown) {
                alert(jqXHR.status + ' ' + jqXHR.responseText);
            }
        });
    }

    var RestPost = function () {
        $.ajax({
            type: 'POST',
            url: prefix,
            dataType: 'json',
            async: true,
            success: function (result) {
                alert('Время: ' + result.time
                        + ', сообщение: ' + result.message);
            },
            error: function (jqXHR, textStatus, errorThrown) {
                alert(jqXHR.status + ' ' + jqXHR.responseText);
            }
        });
    }

</script>

<body>

<h3>Online shop</h3>

<button type="button" onclick="RestGet()">GET</button>
<button type="button" onclick="RestPost()">POST</button>

</body>
</html>
