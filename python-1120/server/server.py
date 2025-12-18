from flask import Flask, render_template, request, jsonify, session, redirect, url_for
# 초기화
app = Flask(__name__)
app.secret_key = '@human123#'

users = [{
        'id' : 'id1',
        'password' : '1234',
        'addr' : '천안',
        'level' : 'admin'
    },
    {
        'id' : 'id2',
        'password' : '12345',
        'addr' : '천안2',
        'level' : 'staff'
    }]

@app.route('/home')
def home() :
    a = 10
    print(a)
    return '''
        <h1>hello world</h1>
        <h2>name : woo</h2>
    '''
    
@app.route('/login2')
def login() :
    return render_template('login.html')

# @app.route('/login_check', methods=['POST', 'GET'])
# @app.route('/login_check', methods=['POST'])
def login_check() :
    print('로그인 체크 시작')
    
    print("request.method", request.method)
    
    # 파라미터 받기
    id = ''
    pw = ''
    # GET 방식
    if request.method == 'GET' :
        id = request.args.get('id')
        print('[GET] id: ', id)
        pw = request.args.get('pw')

    # POST 방식
    elif request.method == 'POST' :
        pw = request.form.get('pw')
        print('[POST] pw: ', pw)
    
    # return f'입력한 id:{id}, pw:{pw}'
    return render_template('main.html', id2=id, d={"k":"v"}, l=[1,2,3,4])

# 주소로 전달인자 받기
@app.route('/echo1/<name>')
def echo1(name) :
    return f'<h1>name : {name}</h1>'

# 주소 값이 타입 지정
@app.route('/echo2/<int:id>')
def echo2(id) :
    return f'<h1>id : {id}</id>'

@app.route('/join', methods=['POST'])
def join() :
    param = request.get_json() # json 받아서
    print(type(param), param)
    print('id', param.get('id'))
    param['response'] = 'ok'
    
    return jsonify(param)

@app.route('/main1')
def main1() :
    return render_template('main1.html')

@app.route('/main2')
def main2() :
    print("session.get('user_info)", session.get('user_info'))
    if 'user_info' in session :
        return render_template('main2.html', user=session.get('user_info'))
    else :
       return redirect('/login2')
        
@app.route('/login_check2', methods=['POST', 'GET'])
def login_check2() :
    id = request.args.get('id')
    pw = request.args.get('pw')
    
    for user in users :
        if ( user['id'] == id) and (user['password'] == pw) :
            print('회원 맞음')
            session['user_info'] = user
            # return render_template('main1.html', user=user) 
            return redirect('/main2') 

    return render_template('login.html')
@app.before_request
def check() :
    print('모든것의 처음 부분')
    print('request.endpoint', request.endpoint)
    print('request.remote_addr', request.remote_addr)
    print("request.headers.get('User-Agent')", request.headers.get('User-Agent'))
    
    # 로그인 없이 통과하는 메소드
    if request.endpoint in ['login', 'logout', None] :
        return
    
    if 'user_info' not in session :
        return redirect( url_for('login'))
# 서버 구동
# 기본 포트 : 5000
app.run(port=5000, debug=True)
