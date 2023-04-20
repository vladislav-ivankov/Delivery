$('td[contenteditable=true]').blur(function() {
    var field = $(this).attr('field');
    var value = $(this).text();
    var id = $(this).parent().attr('id');
    $.ajax({
        url: '/admin/update',
        type: 'POST',
        data: { id: id, field: field, value: value },
        success: function(response) {
            console.log(response);
            $(this).css('background-color', 'green');
        }.bind(this),
        error: function(error) {
            console.log(error);
            $(this).css('background-color', 'red');
        }.bind(this)
    });
});

$('#save-changes-btn').click(function() {
    $('td[contenteditable=true]').each(function() {
        var field = $(this).attr('field');
        var value = $(this).text();
        var id = $(this).parent().attr('id');
        var $elem = $(this); // сохраняем ссылку на элемент
        $.ajax({
            url: '/admin/update',
            type: 'POST',
            data: { id: id, field: field, value: value },
            success: function(response) {
                console.log(response);
                $elem.css('background-color', 'green'); // используем сохраненную ссылку на элемент
            },
            error: function(error) {
                console.log(error);
                $elem.css('background-color', 'red'); // используем сохраненную ссылку на элемент
            }
        });
    });
});