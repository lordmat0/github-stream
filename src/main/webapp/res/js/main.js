$(function () {

    setInterval(function () {
        var date = $('.commit').first().find('.commit-date strong').text().trim();
        
        $.ajax('rest/githubapi/commit/new', {
            contentType: 'application/json',
            type: 'POST',
            data: JSON.stringify({
                "data": date
            })
        });

    }, 60000);



});


