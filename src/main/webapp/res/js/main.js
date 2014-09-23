$(function () {

    $(window).scroll(function(){
        var ajaxCall = false;
        return function(){
            if(ajaxCall){
                return; // already Calling
            }
            
            
            var totalHeight = document.body.offsetHeight;
            var visibleHeight = document.documentElement.clientHeight;

            var scrollTop = document.documentElement.scrollTop;

            // Where the scroll bar is currently
            var currentScroll = scrollTop ? scrollTop : document.body.scrollTop;

            if (totalHeight <= visibleHeight + currentScroll) {
                // At bottom of the page
                ajaxCall = true;
                var date = $('.commit').last().find('.commit-date strong').text();

                $.ajax('rest/githubapi/commit/old', {
                    contentType: 'application/json',
                    type: 'POST',
                    data: JSON.stringify({
                        "data": date
                    }),
                    success: function(data){handleOldCommits(data); ajaxCall = false;}
                });
            }
        };
    }());

    /*
    $(window).scroll(function () {
        var totalHeight = document.body.offsetHeight;
        var visibleHeight = document.documentElement.clientHeight;

        var scrollTop = document.documentElement.scrollTop;

        // Where the scroll bar is currently
        var currentScroll = scrollTop ? scrollTop : document.body.scrollTop;

        if (totalHeight <= visibleHeight + currentScroll) {
            // At bottom of the page
            
            var date = $('.commit').last().find('.commit-date strong').text();
            
            $.ajax('rest/githubapi/commit/old', {
                contentType: 'application/json',
                type: 'POST',
                data: JSON.stringify({
                    "data": date
                }),
                success: handleOldCommits
            });
        }

    });
    */



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
    var fadeTime = 1500;
    
    for (var i = 0; i < data.length; i++) {
        var $commit = createCommit(data[i]);

        // Add the new commit to the DOM
        //$commit.prepend($('section')).fadeIn(1000);
        
        $('section').prepend($commit.fadeIn(fadeTime));
        fadeTime += 50;
    }
}

/**
 * 
 * @param {type} data POST data returned from Ajax call
 */
function handleOldCommits(data) {
    var fadeTime = 1500;
    
    for (var i = 0; i < data.length; i++) {
        var $commit = createCommit(data[i]);

        // Add the new commit to the DOM
        $('section').append($commit.fadeIn((fadeTime)));
        fadeTime += 50;
    }
    ajaxCall = false;
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
    
    var date = data.date;
    
    // Add Z to the ender if not appended on at the server (seems to be an issue with osx)
    date = date + (date.indexOf("Z") > -1 ? '' : 'Z');
    
    // Change values to new commit
    $commit.find('.commit-id strong').text(data.id);
    $commit.find('.commit-message div').text(data.message);
    $commit.find('.commit-date strong').text(date);
    $commit.find('.commit-accounturl')
            .attr('href', user.accountUrl)
            .find('strong').text(user.userName);

    $commit.find('.commit-user-avatar').attr('src', user.avatarUrl);
    
    $commit.hide();

    return $commit;
}