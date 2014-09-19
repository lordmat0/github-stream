$(function () {

    setInterval(function () {
        var date = $('.commit').first().find('.commit-date strong').text().trim();

        $.ajax('rest/githubapi/commit/new', {
            contentType: 'application/json',
            type: 'POST',
            data: JSON.stringify({
                "data": date
            }),
            success: handleNewCommits
        });

    }, 5000);
});


function handleNewCommits(data) {
    if (!data.length) {
        // data is empty
        return;
    }

    for (var i = 0; i < data.length; i++) {
        // Clone existing object
        var $commit = $('.commit').first().clone(false);
        
        var user = data[i].userCommited;

        // Change values to new commit
        $commit.find('.commit-id').text(data[i].id);
        $commit.find('.commit-message').text(data[i].message);
        $commit.find('.commit-date').text(data[i].date)
        $commit.find('.commit-accounturl').attr('id', user.accountUrl).text(user.userName);
        $commit.find('.commit-user-avatar').attr('src', user.avatarUrl);
        
        
        $('section').prepend($commit);

    }
}