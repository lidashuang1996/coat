import{d,r as a,a as m,o as h,A as r,b,c as u,e as t,t as e,f as w,w as y,g as f,h as p,i as v,j as g,_ as k}from"./index-3a90435b.js";const $={key:0,class:"me"},C={class:"me-container"},M={class:"me-title"},N={class:"me-content"},V={class:"me-table"},B={class:"me-table-row"},D={class:"me-table-row-content"},A={class:"me-table-col",style:{width:"30%"}},L={class:"me-table-col",style:{width:"70%"}},U={class:"me-table-row"},j={class:"me-table-row-content"},E={class:"me-table-col",style:{width:"30%"}},I={class:"me-table-col",style:{width:"70%"}},S={class:"me-table-row"},T={class:"me-table-row-content"},q={class:"me-table-col",style:{width:"30%"}},z={class:"me-table-col",style:{width:"70%"}},F={class:"me-table-row"},G={class:"me-table-row-content"},H={class:"me-table-col",style:{width:"30%"}},J={class:"me-table-col",style:{width:"70%"}},K={class:"me-table-row"},O={class:"me-table-row-content"},P={class:"me-table-col",style:{width:"30%"}},Q={class:"me-table-col",style:{width:"70%"}},R={class:"me-table-row"},W={class:"me-table-row-content"},X={class:"me-table-col",style:{width:"30%"}},Y={class:"me-table-col",style:{width:"70%"}},Z=d({__name:"Me",setup(x){const l=a(!1),c=a(!1),o=m({id:"",status:-1,name:"",nickname:"",describe:"",account:""});h(async()=>{await r.init()&&(await n(),l.value=!0)});const n=async()=>{console.log("getUserData")},i=()=>{console.log("onLogout")};return(s,tt)=>{const _=v("a-button");return l.value?(b(),u("div",$,[t("div",C,[t("div",M,e(s.$t("me.title")),1),t("div",N,[t("ul",V,[t("li",B,[t("ul",D,[t("li",A,e(s.$t("me.info.id")),1),t("li",L,e(o.id),1)])]),t("li",U,[t("ul",j,[t("li",E,e(s.$t("me.info.status")),1),t("li",I,e(o.status),1)])]),t("li",S,[t("ul",T,[t("li",q,e(s.$t("me.info.name")),1),t("li",z,e(o.name),1)])]),t("li",F,[t("ul",G,[t("li",H,e(s.$t("me.info.nickname")),1),t("li",J,e(o.nickname),1)])]),t("li",K,[t("ul",O,[t("li",P,e(s.$t("me.info.account")),1),t("li",Q,e(o.account),1)])]),t("li",R,[t("ul",W,[t("li",X,e(s.$t("me.info.describe")),1),t("li",Y,e(o.describe),1)])])]),w(_,{type:"primary",class:"me-button",onClick:f(i,["stop"])},{default:y(()=>[g(e(c.value?"":s.$t("me.button.text")),1)]),_:1},8,["onClick"])])])])):p("",!0)}}});const st=k(Z,[["__scopeId","data-v-281c1d9c"]]);export{st as default};
