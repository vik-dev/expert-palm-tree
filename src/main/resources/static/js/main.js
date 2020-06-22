var userListApi = Vue.resource('/user/list');
var saveUserApi = Vue.resource('/user/save');
var roleListApi = Vue.resource('/user/role_list')
var removeUserApi = Vue.resource('/user/delete{id}')


Vue.component('user-form', {
    props: ['users'],
    data: function(){
        return {
            user: {
                id: '',
                name: '',
                login: '',
                password: '',
                roles: []
            },
            roles: []
        }
    },
    template:
        '<div>' +
        '   <div class="user_form">' +

        '       <label for="roles">Имя: </label>' +
        '       <input type="text" name="name" placeholder="имя" v-model="user.name">' +

        '       <label for="roles">Логин: </label>' +
        '       <input type="text" name="login" placeholder="логин" v-model="user.login">' +

        '       <label for="roles">Пароль: </label>' +
        '       <input type="password" v-model="user.password" placeholder="пароль">' +

        '       <label for="roles">Роль: </label>' +
        '       <select multiple id="roles" v-model="user.roles">' +
        '           <option v-for="role in roles" v-bind:value="role.id">{{ role.name }}</option>' +
        '       </select>' +

        '       <input type="button" @click="saveUser" value="Сохранить">' +
        '   </div>' +
        '</div>',
    methods: {
        saveUser: function () {
            var user = {
                name: this.user.name,
                login: this.user.login,
                password: this.user.password,
                roles: this.user.roles,
                id: this.user.id
            }
            saveUserApi.save({}, user).then(result => {
                result.json().then(data => {
                    if (!data.status){
                        let al = "Список ошибок: \n";
                        data.errors.forEach(el=>{
                            al += el.description + ";\n";
                        })
                        alert(al); //TODO: заменить на custom alert, как вариант на https://vuejsexamples.com/simple-alert-for-vue-js/
                    } else {
                        this.users.push(data.user)
                    }
                })
            })
        }
    },
    created: function () {
        roleListApi.get().then(result => {
            result.json().then(data => {
                data.forEach( el => this.roles.push(el))
            })
        })
    }
})

Vue.component('user-list', {
    props: ['users'],
    template:
        '<div>' +
        '   <table class="user_list">' +
        '      <thead>' +
        '          <th>Имя</th>' +
        '          <th>Логин</th>' +
        '          <th>Пароль</th>' +
        '      </thead>' +
        '      <tbody>' +
        '          <tr v-for="user in users" :key ="user.login" :user="user">' +
        '              <td>{{ user.name }}</td>' +
        '              <td>{{ user.login }}</td>' +
        '              <td>{{ user.password }}</td>' +
        '              <td><button v-on:click="removeUser(user.id)">Удалить пользователя</button></td>' +
        '          </tr>' +
        '      </tbody>' +
        '   </table>' +
        '</div>',
    created: function () {
        userListApi.get().then( result => {
            result.json().then(data => {
                data.forEach( el => this.users.push(el))
            })
        })
    },
    methods: {
        removeUser: function (id) {
            removeUserApi.delete({id: id}).then((el)=>{
                for (let i = 0; i < this.users.length; i++) {
                    if (this.users[i].id === id) {
                        this.users.splice(i, 1);
                        break;
                    }
                }
            });
        }
    }
})

var app = new Vue({
    el: '#user-list',
    template:
        '<div class="main">' +
        '   <div>' +
        '       <p>Список пользователей</p>' +
        '       <user-list :users = "users"/>' +
        '   </div>' +
        '   <hr>' +
        '   <div>' +
        '       <p>Добавить пользователя</p>' +
        '       <user-form :users = "users"/>' +
        '   </div>' +
        '</div>',
    data: {
        users: []
    }
})