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
        $commit.find('.commit-id strong').text(data[i].id);
        $commit.find('.commit-message div').text(data[i].message);
        $commit.find('.commit-date strong').text(data[i].date)
        $commit.find('.commit-accounturl')
                .attr('href', user.accountUrl)
                .find('strong').text(user.userName);
        
        $commit.find('.commit-user-avatar').attr('src', user.avatarUrl);
        
        // Add the new commit to the DOM
        $('section').prepend($commit);
    }
}