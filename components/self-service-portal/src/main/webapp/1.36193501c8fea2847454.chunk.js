webpackJsonp([1],{1004:function(t,o){t.exports='.auth-main{display:flex;align-items:center;height:100%;width:100%;position:absolute}.auth-block{width:540px;margin:0 auto;border-radius:5px;background:rgba(0,0,0,0.55);color:#fff;padding:32px}.auth-block h1{font-weight:300;margin-bottom:28px;text-align:center}.auth-block p{font-size:16px}.auth-block a{text-decoration:none;outline:none;transition:all 0.2s ease;color:#00abff}.auth-block a:hover{color:#0091d9}.auth-block .control-label{padding-top:11px;color:#fff}.auth-block .form-group{margin-bottom:12px}.auth-input{width:300px;margin-bottom:24px}.auth-input input{display:block;width:100%;border:none;font-size:16px;padding:4px 10px;outline:none}a.forgot-pass{display:block;text-align:right;margin-bottom:-20px;float:right;z-index:2;position:relative}.auth-link{display:block;font-size:16px;text-align:center;margin-bottom:33px}.auth-sep{margin-top:36px;margin-bottom:24px;line-height:20px;font-size:16px;text-align:center;display:block;position:relative}.auth-sep>span{display:table-cell;width:30%;white-space:nowrap;padding:0 24px;color:#fff}.auth-sep>span>span{margin-top:-12px;display:block}.auth-sep:before,.auth-sep:after{border-top:solid 1px #fff;content:"";height:1px;width:35%;display:table-cell}.al-share-auth{text-align:center}.al-share-auth .al-share{float:none;margin:0;padding:0;display:inline-block}.al-share-auth .al-share li{margin-left:24px}.al-share-auth .al-share li:first-child{margin-left:0}.al-share-auth .al-share li i{font-size:24px}.btn-auth{color:#fff !important}\n'},1014:function(t,o){t.exports='\n<div class="auth-main">\n  <div class="auth-block">\n    <img ng-reflect-src="assets/img/logo_mobileconnect.png" class="offset-xs-3 offset-md-2 offset-lg-4 offset-sm-4 card-title" style="width: 160px;height:auto;" src="assets/img/logo_mobileconnect.png">\n    <br>\n    <!-- <a routerLink="/register" class="auth-link">New to Mobile connect? Register!</a> -->\n\n    <form *ngIf="!callback" [formGroup]="form" (ngSubmit)="onSubmit(form.value)" class="form-horizontal">\n      <div class="form-group row" [ngClass]="{\'has-error\': (!phone.valid && phone.touched), \'has-success\': (phone.valid && phone.touched)}">\n        <label for="phoneInput" class="col-sm-3 control-label">Mobile Number</label>\n\n        <div class="col-sm-7">\n          <input [formControl]="phone" type="text" class="form-control" id="phoneInput" placeholder="Enter MSISDN" pattern="[0-9]{12}">\n        </div>\n      </div>\n      <div class="form-group row">\n        <div class="offset-sm-5 col-sm-12">\n          <button [disabled]="!form.valid" type="submit" class="btn btn-default btn-auth">Sign in</button>\n        </div>\n      </div>\n    </form>\n\n    <div *ngIf="callback">access_token : {{access_token}}</div>\n  </div>\n</div>\n'},1023:function(t,o,a){var e=a(1004);"string"==typeof e&&(e=[[t.i,e,""]]);a(28)(e,{});e.locals&&(t.exports=e.locals)},965:function(t,o,a){"use strict";Object.defineProperty(o,"__esModule",{value:!0});var e=a(0),n=a(52),i=a(81),s=a(269),r=a(974),l=a(992);a.d(o,"LoginModule",(function(){return c}));var c=(function(){function t(){}return t=__decorate([a.i(e.i)({imports:[n.b,i.b,i.a,s.a,l.a],declarations:[r.a]}),__metadata("design:paramtypes",[])],t)})()},974:function(t,o,a){"use strict";var e=a(0),n=a(43),i=a(81),s=a(554),r=a(1023);a.n(r);a.d(o,"a",(function(){return l}));var l=(function(){function t(t,o,a){this.router=a,this.submitted=!1,this.callback=!1,this.form=t.group({phone:["",i.c.compose([i.c.required])]}),this.phone=this.form.controls.phone,this.access_token=o.snapshot.params.access_token,this.error=o.snapshot.params.error,this.access_token&&(this.callback=!0,this.doLogin(this.access_token)),this.error&&alert(this.error)}return t.prototype.onSubmit=function(t){this.submitted=!0,this.form.valid&&(window.location.href=s.a.getAuthUrl(this.phone.value))},t.prototype.doLogin=function(t){localStorage.setItem("access_token",t),this.router.navigate(["/pages/dashboard"])},t=__decorate([a.i(e.Q)({selector:"login",template:a(1014)}),__metadata("design:paramtypes",[i.d,n.c,n.b])],t)})()},992:function(t,o,a){"use strict";var e=a(43),n=a(974);a.d(o,"a",(function(){return s}));var i=[{path:"",component:n.a},{path:"1/:access_token",component:n.a},{path:"0/:error",component:n.a}],s=e.a.forChild(i)}});