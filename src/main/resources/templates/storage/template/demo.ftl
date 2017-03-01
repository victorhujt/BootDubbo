<title>导入模板配置</title>
<div id="app">
    {{loading}}
</div>
<div id="app-2">
  <span v-bind:title="message">
    Hover your mouse over me for a few seconds to see my dynamically bound title!
  </span>
</div>
<div id="app-3">
    <p v-if="seen">Now you see me</p>
</div>
<div id="app-4">
    <ol>
        <li v-for="todo in todos">
            {{ todo.text}}
        </li>
    </ol>
</div>
<div id="app-5">
    <p>{{message}}</p>
    <button v-on:click="reverseMessage">Reverse Message</button>
</div>
<div id="app-6">
    <p>{{message}}</p>
    <input v-model="message">
</div>
=================
<ol>
    <todo-item></todo-item>
</ol>
=================

<div id="app-7">
    <ol>
        <todo-item v-for="item in groceryList" v-bind:todo="item"></todo-item>
    </ol>
</div>

<#--<div id="example">-->
<#--</div>-->


<script type="text/javascript" >

    var MyComponent = Vue.extend({

    })

    var myComponentInstance = new MyComponent()

    var vm = new Vue({
        data:{
            a:1
        },
        created:function () {
            console.log('a is : ' + this.a)
        }
    })



    //    var data = {a : 1}
    //    var vm = new Vue({
    //        data:data
    //    })
    //    console.log(vm.a == data.a)
    //    vm.a = 2
    //    console.log(data.a)
    //    data.a = 3
    //    console.log(vm.a)
    //
    //    var data = {a: 1}
    //    var vm = new Vue({
    //        el:'#example',
    //        data:data
    //    });
    //    vm.$watch('a',function (newVal,oldVal) {
    //        //这个回调将在 'vm.a' 改变后调用
    //        console.log("edfrgthyjukio")
    //    });





    Vue.component('todo-item',{
        props:['todo'],
        template:'<li>{{todo.text}}</li>'
    })

    var app7 = new Vue({
        el:'#app-7',
        data:{
            groceryList:[
                {text:'AA11'},
                {text:'AA22'},
                {text:'AA33'}
            ]
        }
    })




    var app6 = new Vue({
        el:'#app-6',
        data:{
            message:'Hello Vue!'
        }
    })
    var app5 = new Vue({
        el:'#app-5',
        data:{
            message:'luo ying hao',
        },
        methods:{
            reverseMessage:function () {
                this.message = this.message.split('').reverse().join('')
            }
        }
    })
    var app = new Vue({
        el:'#app',
        data:{
            loading:'Hello Vue!'
        }
    });
    var app2 = new Vue({
        el:'#app-2',
        data:{
            message: 'You load this page on ' + new Date()
        }
    })
    var app3 = new Vue({
        el:'#app-3',
        data:{
            seen:true
        }
    })
    var app4 = new Vue({
        el:'#app-4',
        data:{
            todos:[
                {text: 'a111111'},
                {text: 'b222222'},
                {text: 'c333333'}
            ]
        }
    })

</script>