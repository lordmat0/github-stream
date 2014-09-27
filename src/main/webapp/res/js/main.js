$(function () {

    // If there are no commits on the page return, the JSP will handle the DOM
    if (!$('.commit').length) {
        // Hide loading gif
        $('#img-loading').hide();
        
        return;
    }


    $(window).scroll(function () {
        var ajaxCall = false;
        return function () {
            if (ajaxCall) {
                return; // already Calling
            }

            var totalHeight = document.body.offsetHeight;
            var visibleHeight = document.documentElement.clientHeight;

            var scrollTop = document.documentElement.scrollTop;

            // Where the scroll bar is currently
            var currentScroll = scrollTop ? scrollTop : document.body.scrollTop;

            if (totalHeight <= visibleHeight + currentScroll) {
                // At bottom of the page

                // Stop other ajaxCalls
                ajaxCall = true;

                // Show loading gif
                $('#img-loading').show();

                // Get the date of the last commit
                var date = $('.commit').last().find('.commit-date strong').text();

                $.ajax('rest/githubapi/commit/old', {
                    contentType: 'application/json',
                    type: 'POST',
                    data: JSON.stringify({
                        "data": date
                    }),
                    success: function (data) {
                        handleOldCommits(data);

                        // Allow other ajax calls as we've finished adding commits
                        ajaxCall = false;

                        // Hide loading gif
                        $('#img-loading').hide();
                    }
                });
            }
        };
    }());


    /**
     * 
     * @param {type} data Expected data = { userCommited, date, id, message, userCommited: { accountUrl, userName, avatarUrl }
     * 
     * @returns {createCommit.$commit|jQuery} A 
     */
    createCommit = (function (data) {
        // master commit here, fixes bug with cloning a fading element
        // clone a commit on the DOM
        var $masterCommit = $('.commit').first().clone(false);

        // Closure scope
        return function (data) {
            var $commit = $masterCommit.clone(false);

            var user = data.userCommited;

            var date = data.date;

            // Add Z to the ender if not appended on at the server (seems to be an issue with osx)
            date = date + (date.indexOf("Z") > -1 ? '' : 'Z');

            // Change values to new commit
            $commit.find('.commit-id').attr('href', data.idUrl)
                    .text(data.id);
            $commit.find('.commit-message div').text(data.message);
            $commit.find('.commit-date strong').text(date);
            $commit.find('.commit-accounturl')
                    .attr('href', user.accountUrl)
                    .text(user.userName);

            $commit.find('.commit-user-avatar').attr('src', user.avatarUrl);

            $commit.hide();

            return $commit;
        };
    }());


    // Check for new Commits
    setInterval(function () {
        var date = $('.commit').first().find('.commit-date strong').text();

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
    var delayTime = 0;

    for (var i = 0; i < data.length; i++) {
        var $commit = createCommit(data[i]);

        // Add the new commit to the DOM
        $('section').prepend($commit.delay(delayTime).fadeIn(1500));
        delayTime += 50;
    }
}

/**
 * 
 * @param {type} data POST data returned from Ajax call
 */
function handleOldCommits(data) {
    var delayTime = 0;

    for (var i = 0; i < data.length; i++) {
        var $commit = createCommit(data[i]);

        // Add the new commit to the DOM
        $('section').append($commit.delay(delayTime).fadeIn(1500));
        delayTime += 50;
    }
    ajaxCall = false;
}


