var vm=new Vue({
  el:"body",
  data:{
    loginName:"",
    loginPwd:"",
    systemId:"",
    ufade:false,
    pfade:false,
    ofade:false,
    sfade:false,
    mfade:false,
    uone:false,
    pone:false,
    utwo:false,
    ptwo:false,
    uthree:false,
    pthree:false,
    user:'',
    oper:'',
    part:'',
    loginNameErrorMessage:"",
    loginPwdErrorMessage:"",
    mfields:[
      {date:"10-06",news:"新增 运营身份 报表统计系统"},
      {date:"10-01",news:"服务器维护 19:30 开通"},
      {date:"09-11",news:"新增 伙伴系统 财务系统统计"},
      {date:"09-10",news:"移除 伙伴系统 增值业务查询"},
    ]
  },
  methods:{
    throwback:function(val){
      this.loginName=''
      this.loginPwd=''
      this.uone=false
      this.pone=false
      this.utwo=false
      this.ptwo=false
      this.uthree=false
      this.pthree=false
      if(val=='1'){
        if(this.ufade==true){
          this.ufade=false
          this.oper="fadeOut-anime"
          this.part="fadeOut-anime"
        }else{
          this.ufade=true
          this.pfade=false
          this.ofade=false
          this.user="fadeOut-anime"
          this.oper="fadeIn-anime"
          this.part="fadeIn-anime"
          this.systemId = 1000
        }
      }else if(val=='2'){
        if(this.ofade==true){
          this.ofade=false
          this.user='fadeOut-anime'
          this.part='fadeOut-anime'
        }else{
          this.ofade=true
          this.pfade=false
          this.ufade=false
          this.oper="fadeOut-anime"
          this.user="fadeIn-anime"
          this.part="fadeIn-anime"
            this.systemId = 2000
        }
      }else if(val=='3'){
        if(this.pfade==true){
          this.pfade=false
          this.user='fadeOut-anime'
          this.oper='fadeOut-anime'
        }else{
          this.pfade=true
          this.ufade=false
          this.ofade=false
          this.part="fadeOut-anime"
          this.user="fadeIn-anime"
          this.oper="fadeIn-anime"
          this.systemId = 3000
        }
      }
    },
    showhidden:function(){
      if(this.mfade==true){
        this.mfade=false
        this.roll=""
      }else{
        this.mfade=true
        this.roll="play"
      }
    },
    tnt:function(val){
      if(val==1){
        if(this.loginName=="" || this.loginName==null){
        this.loginNameErrorMessage="请填写用户名"
        this.uone=true
        this.utwo=false
        this.uthree=false
        }else{
          this.uone=false
        }
      }else if(val==2){
        if(this.loginName=="" || this.loginName==null){
          this.loginNameErrorMessage="请填写用户名"
          this.utwo=true
          this.uone=false
          this.uthree=false
        }else{
          this.utwo=false
        }
      }else if(val==3){
        if(this.loginName=="" || this.loginName==null){
          this.loginNameErrorMessage="请填写用户名"
          this.uthree=true
          this.utwo=false
          this.uone=false
        }else{
          this.uthree=false
        }
      }
    },
    tpt:function(val){
      if(val==1){
        if(this.loginPwd=="" || this.loginPwd==null){
          this.loginPwdErrorMessage ="请填写密码"
          this.pone=true
          this.ptwo=false
          this.pthree=false
        }else{
          this.pone=false
        }
      }else if(val==2){
        if(this.loginPwd=="" || this.loginPwd==null){
          this.loginPwdErrorMessage ="请填写密码"
          this.ptwo=true
          this.pone=false
          this.pthree=false
        }else{
          this.ptwo=false
        }
      }else if(val==3){
        if(this.loginPwd=="" || this.loginPwd==null){
          this.loginPwdErrorMessage ="请填写密码"
          this.pthree=true
          this.pone=false
          this.ptow=false
        }else{
          this.pthree=false
        }
      }
    },
    jump:function(){
      window.open("http://shang.qq.com/email/stop/email_stop.html?qq=314719&sig=32fadd9314c63410295b30f4f78794aedebafd86a16da285&tttt=1")
    },
    onSubmit: function (e) {
      var  self = this;
      // if(this.loginName == "" || this.loginName == null){
      //   console.info("name " +this.loginName);
      //   self.loginNameErrorMessage ="请填写用户名";
      //   return;
      // }else{
      //   self.loginNameErrorMessage ="";
      // }

      // if(this.loginPwd == "" || this.loginPwd == null){
      //   console.info("pwd " + this.loginPwd);
      //   self.loginPwdErrorMessage ="请填写密码"
      //   return;
      // }else{
      //   self.loginPwdErrorMessage ="";
      // }

      // validate manually
      Vue.http.post('/login', {
        loginName : self.loginName,
        loginPwd: self.loginPwd,
        systemId: self.systemId
    }).then(
      function (response) {
        var a   = response.json();

        if(response.body.code == 200){
          window.location.href="/index";
        }else{
          var message = response.body.message;
          if(message =="用户名不存在"){
            self.loginNameErrorMessage = response.body.message
          }else{
            self.loginPwdErrorMessage = response.body.message
          }
        }
        console.log(response);
      }, function (err) {
        alert("未知异常");
      }
    );
    }
  }
})