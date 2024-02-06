$(function(){
    function getRandomInt(max) {
        return Math.floor(Math.random() * max);
    }
    let getSuccessText = function(id) {
        let item = $('<div class="message-item"></div>');
        item.text('Запись по id: ' + id + ' найдена!');
        return item;
    };
    let getDeleteText = function(id) {
        let item = $('<div class="message-item"></div>');
        item.text('Запись по id: ' + id + ' удалена!');
        return item;
    };
    let getErrorText = function(id) {
        let item = $('<div class="message-item"></div>');
        item.text('Запись по id: ' + id + ' не найдена!');
        return item;
    };

    let setNewTaskText = function() {
        let item = $('<div class="message-item"></div>');
        item.text('Создана новая задача!');
        return item;
    };

    $.get('datetime', {}, function(response){
       $('.date-and-time').append(response)

    })

    $.get('/numbers', {}, function(response){
           $('.random-number').append(getRandomInt(10))
        })
    $('.send-id-task').on('click', function(){
        let id = $('.set-id-task').val()
        if (id == '') {
            alert('Не введен ID задачи!')
            return
        }
        $.ajax({
            method: "GET",
            url: '/tasks/' + id,
            success: function(response){
                $('.list-task').append(getSuccessText(id))
            },
            error: function(response){
                if(response.status == 404) {
                    $('.list-task').append(getErrorText(id))
                }
            }
        })
        $('.set-id-task').val('')
    })
    $('.delete-task').on('click', function(){
        let id = $('.set-id-task').val()
        if (id == '') {
            alert('Не введен ID задачи!')
            return
        }
        $.ajax({
            method: "DELETE",
            url: '/tasks/' + id,
            success: function(response){
                $('.list-task').append(getDeleteText(id))
            },
            error: function(response){
                if(response.status == 404) {
                    $('.list-task').append(getErrorText(id))
                }
            }
        })
        $('.set-id-task').val('')
    })
    $('.send-task').on('click', function(){
        let newTitle = $('.new-title').val()
        let newDescription = $('.new-description').val()
        if (newTitle == '') {
            alert('Не введено название задачи!')
            return
        }
        if (newDescription == '') {
            alert('Не введено описание задачи!')
            return
        }
        $.post('/tasks', {newTitle:newTitle, newDescription: newDescription}, function(response){})
        $('.list-task').append(setNewTaskText())
        $('.new-title').val('')
        $('.new-description').val('')
    })
    $('.get-all-tasks').on('click', function(){
        $.ajax({
            method: "GET",
            url: '/tasks',
            success: function(response){
                $('.list-task').append('Количество записей: ' + response.length)
            },
        })
    })
    $('.change-task').on('click', function(){
        $('.boolean-column').css({display: 'flex'})
        $('.changed-record').css({display: 'block'})
        $('.change-task').css({display: 'none'})
        $('.send-task').css({display: 'none'})
    })
    $('.changed-record').on('click', function(){
        $('.boolean-column').css({display: 'none'})
        $('.changed-record').css({display: 'none'})
        $('.change-task').css({display: 'block'})
        $('.send-task').css({display: 'block'})
        let title = $('.new-title').val()
        let description = $('.new-description').val()
        let isDone = $('.boolean-text').val()
        let id = $('.set-id-task').val()
        if (id == '') {
           alert('Не введен ID задачи!')
           return
        }
        $.ajax({
            method: "PATCH",
            url: '/tasks/' + id,
            data: {title:title, description:description, isDone:isDone},
            success: function(response){
                $('.new-title').val('')
                $('.new-description').val('')
                $('.set-id-task').val('')
                $('.boolean-text').val('')
            },
            error: function(response){
                if(response.status == 404) {
                    $('.list-task').append(getErrorText(id))
                }
            }
        })
    })

})






