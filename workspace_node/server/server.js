const express = require('express');
const app = express();
const appRoot = require('app-root-path').path
const session = require('express-session')
const bodyParser = require('body-parser');

const port = 3000;
app.listen(port, function(){
    console.log("서버 켜짐", port, appRoot)
})

app.set('views', appRoot+'/ui/views')
app.set('view engine', 'ejs')

app.use(bodyParser.urlencoded({ extended:false }))
app.use(bodyParser.json())

app.use( express.static(appRoot+'/ui/public'))
app.use( session({
    secret: 'tq*******',
    resave: false,              // 세션 내용이 바뀌지 않으면 저장 안함
    saveUninitialized: true,    // 로그인 안 한 사용자의 세션 저장 안함
    rolling: true,              // 활동이 있으면 로그인 풀림
    cookie: { macAge: 30860*1000 }  // 30분 후 로그인 풀림
}) )
app.use(aop)


app.get('/hello', function(req, resp){
    console.log('hello express')
})

app.get('/echo', function(req, resp){
    // get 방식일 때 파라메터
    const id = req.query.id; 
    console.log('id', id)
    resp.send('id'); // 클라이언트에 응답 보내기
})

app.get('/json', function(req, resp){
    const j = {
        a: 1,
        b: "eng",
        c: "한글"
    }
    resp.send(j)
})

app.get('/ejs', function(req, resp){
    resp.render('hello')
})

app.get('/login', function(req, resp){
    const pw = req.query.pw
    if( pw == '1234'){
        req.session.user = {
            login: true
        }
        resp.send('성공')
    } else{
        resp.send('실패')
    }
})
app.get('/main', function(req, resp){
    if(req.session.user && req.session.user.login){
        resp.send('secret')
    } else{
        resp.send('로그인 좀')
    }
})

app.get('/delay', async function(req, resp){
    await delay(2000)
    resp.send('2초')
})

app.post('/ajax', function(req, resp){
    console.log(req.body.name, req.body.addr)

    resp.send(req.body)
})

function aop(req, resp, next){
    const before = new Date().getTime()
    
    next()

    resp.on('finish', function(){
        const after = new Date().getTime()
        console.log(after-before);
    })
}

function delay(ms){
    return new Promise(function(resolve, reject){
        setTimeout(function(){
            resolve()
        }, ms)
    })
}
