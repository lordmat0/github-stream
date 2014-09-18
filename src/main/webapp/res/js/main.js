$(function () {

    setInterval(function () {
        $.ajax('rest/githubapi/commit/new', {
            contentType: 'application/json',
            type: 'POST',
            data: JSON.stringify({
                "data": "2014-09-17T22:16:06Z"
            })
        });

    }, 60000);



});


