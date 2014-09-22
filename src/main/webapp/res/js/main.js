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

/**
 * Handles new commits by appending them to the top of the DOM
 * 
 * @param {type} data POST data returned from Ajax call
 */
function handleNewCommits(data) {

    for (var i = 0; i < data.length; i++) {
        var $commit = createCommit(data[i]);

        // Add the new commit to the DOM
        $('section').prepend($commit);
    }
}

/**
 * 
 * @param {type} data POST data returned from Ajax call
 */
function handleOldCommits(data) {
    for (var i = 0; i < data.length; i++) {
        var $commit = createCommit(data[i]);

        // Add the new commit to the DOM
        $('section').append($commit);
    }
}


/**
 * 
 * @param {type} data Expected data = { userCommited, date, id, message, userCommited: { accountUrl, userName, avatarUrl }
 * 
 * @returns {createCommit.$commit|jQuery} A 
 */
function createCommit(data) {
    // Clone existing object
    var $commit = $('.commit').first().clone(false);

    var user = data.userCommited;
    var date = data.date.substring(0, data.date.lastIndexOf('.')) + 'Z';


    // Change values to new commit
    $commit.find('.commit-id strong').text(data.id);
    $commit.find('.commit-message div').text(data.message);
    $commit.find('.commit-date strong').text(date);
    $commit.find('.commit-accounturl')
            .attr('href', user.accountUrl)
            .find('strong').text(user.userName);

    $commit.find('.commit-user-avatar').attr('src', user.avatarUrl);


    return $commit;
}