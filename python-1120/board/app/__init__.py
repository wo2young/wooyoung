from flask import Flask

def create_app():
    app = Flask(__name__)

    # 여기 추가 — 절대 빼먹으면 안 되는 부분
    app.secret_key = "anything-you-want-but-secret"   # 랜덤 문자열이면 OK

    from .routes import bp as board_bp
    app.register_blueprint(board_bp)

    return app