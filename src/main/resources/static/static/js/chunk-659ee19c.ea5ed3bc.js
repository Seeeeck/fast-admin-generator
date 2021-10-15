(window["webpackJsonp"]=window["webpackJsonp"]||[]).push([["chunk-659ee19c"],{"12a5":function(e,t,i){"use strict";i.d(t,"a",(function(){return n})),i.d(t,"b",(function(){return o}));var a=i("b775");function n(){return Object(a["a"])({url:"/config",method:"get"})}function o(e){return Object(a["a"])({url:"/config",method:"post",data:e})}},b775:function(e,t,i){"use strict";i("d3b7");var a=i("bc3a"),n=i.n(a),o=(i("5c96"),n.a.create({baseURL:"http://localhost:8000",timeout:5e3}));o.interceptors.request.use((function(e){return Promise.resolve(e)}),(function(e){return console.log(e),Promise.reject(e)})),o.interceptors.response.use((function(e){var t=e.data;return"application/octet-stream"==t.type?t:0!==t.code?Promise.reject(t):t}),(function(e){return Promise.reject(e)})),t["a"]=o},d55b:function(e,t,i){"use strict";i.r(t);var a=function(){var e=this,t=e.$createElement,i=e._self._c||t;return i("div",{staticClass:"app-container"},[i("br"),i("el-card",{scopedSlots:e._u([{key:"header",fn:function(){return[i("h2",{directives:[{name:"show",rawName:"v-show",value:!e.isExistConfig,expression:"!isExistConfig"}]},[e._v("Please set the global configuration")]),i("h2",{directives:[{name:"show",rawName:"v-show",value:e.isExistConfig,expression:"isExistConfig"}]},[e._v("The global configuration in use")])]},proxy:!0}])},[i("el-form",{ref:"config",attrs:{model:e.config,"label-width":"130px","label-position":"left",rules:e.rules}},[i("el-form-item",{attrs:{label:"Package name",prop:"pkg"}},[i("el-input",{attrs:{placeholder:"com.example.project",disabled:e.isExistConfig},model:{value:e.config.pkg,callback:function(t){e.$set(e.config,"pkg",t)},expression:"config.pkg"}})],1),i("el-form-item",{attrs:{label:"Module name",prop:"moduleName"}},[i("el-input",{attrs:{placeholder:"example",disabled:e.isExistConfig},model:{value:e.config.moduleName,callback:function(t){e.$set(e.config,"moduleName",e._n(t))},expression:"config.moduleName"}})],1),i("el-form-item",{attrs:{label:"Author name",prop:"author"}},[i("el-input",{attrs:{placeholder:"author",disabled:e.isExistConfig},model:{value:e.config.author,callback:function(t){e.$set(e.config,"author",t)},expression:"config.author"}})],1),i("el-form-item",{attrs:{label:"Email",prop:"email"}},[i("el-input",{attrs:{placeholder:"example@email.com",disabled:e.isExistConfig},model:{value:e.config.email,callback:function(t){e.$set(e.config,"email",t)},expression:"config.email"}})],1),i("el-form-item",{attrs:{label:"Table prefix"}},[i("el-input",{attrs:{placeholder:e.isExistConfig?"":"t_",disabled:e.isExistConfig},model:{value:e.config.tablePrefix,callback:function(t){e.$set(e.config,"tablePrefix",t)},expression:"config.tablePrefix"}})],1),i("el-form-item",{attrs:{label:"Language"}},[i("el-select",{model:{value:e.config.language,callback:function(t){e.$set(e.config,"language",t)},expression:"config.language"}},[i("el-option",{key:0,attrs:{value:0,label:"中文"}}),i("el-option",{key:1,attrs:{value:1,label:"日本語"}}),i("el-option",{key:2,attrs:{value:2,label:"English"}})],1)],1),i("el-form-item",{attrs:{label:"Enable swagger"}},[i("el-switch",{attrs:{disabled:e.isExistConfig},model:{value:e.config.enableSwagger,callback:function(t){e.$set(e.config,"enableSwagger",t)},expression:"config.enableSwagger"}})],1),i("el-form-item",{attrs:{label:"Enable lombok"}},[i("el-switch",{attrs:{disabled:e.isExistConfig},model:{value:e.config.enableLombok,callback:function(t){e.$set(e.config,"enableLombok",t)},expression:"config.enableLombok"}})],1),i("el-form-item",{attrs:{label:"Enable Security"}},[i("el-switch",{attrs:{disabled:e.isExistConfig},model:{value:e.config.enableWebSecurity,callback:function(t){e.$set(e.config,"enableWebSecurity",t)},expression:"config.enableWebSecurity"}})],1),i("el-form-item",{directives:[{name:"show",rawName:"v-show",value:!e.isExistConfig,expression:"!isExistConfig"}]},[i("el-button",{attrs:{type:"primary"},on:{click:e.onSubmit}},[e._v("OK")]),i("el-button",{directives:[{name:"show",rawName:"v-show",value:e.onchange,expression:"onchange"}],attrs:{type:"warning"},on:{click:e.onCancel}},[e._v("Cancel")]),i("el-button",{on:{click:e.onReset}},[e._v("Reset")])],1),i("el-form-item",{directives:[{name:"show",rawName:"v-show",value:e.isExistConfig,expression:"isExistConfig"}]},[i("el-button",{attrs:{type:"danger"},on:{click:e.onChange}},[e._v("Change Config")])],1)],1)],1)],1)},n=[],o=i("12a5"),l={data:function(){var e=function(e,t,i){if(""===t)i(new Error("Enter the email"));else{if(""!==t){var a=/^[A-Za-z0-9\u4e00-\u9fa5]+@[a-zA-Z0-9_-]+(\.[a-zA-Z0-9_-]+)+$/;a.test(t)||i(new Error("Enter a valid mail"))}i()}};return{config:{pkg:"",moduleName:"",tablePrefix:"",enableSwagger:!1,enableLombok:!0,enableWebSecurity:!1,author:"",email:"",language:2},isExistConfig:!1,onchange:!1,rules:{pkg:[{required:!0,message:"Enter the package",trigger:"blur"}],moduleName:[{required:!0,message:"Enter the module name",trigger:"blur"}],author:[{required:!0,message:"Enter author name",trigger:"blur"}],email:[{validator:e,trigger:"blur"}]}}},methods:{onCancel:function(){this.fetchData(),this.onchange=!1},onChange:function(){this.isExistConfig=!1,this.onchange=!0},onReset:function(){this.$refs["config"].resetFields()},fetchData:function(){var e=this;Object(o["a"])().then((function(t){e.config=t.data,e.isExistConfig=!0})).catch((function(t){404==t.code&&(e.isExistConfig=!1)}))},onSubmit:function(){var e=this;this.$refs["config"].validate((function(t){t&&e.saveConfig()}))},saveConfig:function(){var e=this;Object(o["b"])(this.config).then((function(t){e.fetchData(),e.$message.success("Successfully saved.")})).catch((function(t){e.$message.error("Save failed")}))}},created:function(){this.fetchData()}},s=l,r=i("2877"),c=Object(r["a"])(s,a,n,!1,null,null,null);t["default"]=c.exports}}]);