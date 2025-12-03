# app/routes.py
from flask import Blueprint, render_template, request, redirect, url_for, session
from .models import selectAll, selectOne, insertBoard, updateBoard, deleteBoard, selectUser, loginUser

bp = Blueprint("board", __name__)


# ====== 목록 ======
@bp.route("/")
def list_page() :
    rows = selectAll()
    return render_template("list.html", info=rows)


# ====== 글쓰기 화면 ======
@bp.route("/write", methods=["GET", "POST"])
def write_page():
    if "user_id" not in session:
        return redirect(url_for("board.login_page"))

    if request.method == "GET":
        return render_template("write.html")

    # POST (실제 글쓰기)
    insertBoard({
        "title": request.form["title"],
        "content": request.form["content"],
        "user_id": session["user_id"]
    })

    return redirect(url_for("board.list_page"))



# ====== 글쓰기 저장 ======
@bp.route("/write", methods=["POST"])
def write_post():
    title = request.form["title"]
    content = request.form["content"]

    insertBoard({
        "title": title,
        "content": content,
        "user_id": session["user_id"]   
    })

    return redirect(url_for("board.list_page"))



# ====== 상세 페이지 ======
@bp.route("/detail/<int:board_id>")
def detail_page(board_id):
    if "user_id" not in session:
        return redirect(url_for("board.login_page"))

    row = selectOne(board_id)
    return render_template("detail.html", info=row)


# ====== 수정 화면 ======
@bp.route("/edit/<int:board_id>", methods=["GET", "POST"])
def edit_page(board_id):
    if "user_id" not in session:
        return redirect(url_for("board.login_page"))

    row = selectOne(board_id)

    # 글쓴이 아닐 때 차단
    if row["user_id"] != session["user_id"]:
        return "권한이 없습니다.", 403

    if request.method == "GET":
        return render_template("edit.html", row=row)

    # POST 수정 처리
    updateBoard({
        "board_id": board_id,
        "title": request.form["title"],
        "content": request.form["content"]
    })

    return redirect(url_for("board.detail_page", board_id=board_id))


# ====== 수정 요청 ======
@bp.route("/edit/<int:board_id>", methods=["POST"])
def edit_post(board_id) :
    title = request.form["title"]
    content = request.form["content"]

    updateBoard({
        "board_id": board_id,
        "title": title,
        "content": content
    })

    return redirect(url_for("board.detail_page", board_id=board_id))


# ====== 삭제 ======
@bp.route("/delete/<int:board_id>")
def delete_page(board_id):
    if "user_id" not in session:
        return redirect(url_for("board.login_page"))

    row = selectOne(board_id)

    # 글쓴이 체크
    if row["user_id"] != session["user_id"]:
        return "권한이 없습니다.", 403

    deleteBoard(board_id)
    return redirect(url_for("board.list_page"))

@bp.route("/login", methods=["GET", "POST"])
def login_page():
    if request.method == "GET":
        return render_template("login.html")

    user_id = request.form["user_id"]
    pw = request.form["user_pw"]

    user = loginUser(user_id, pw)

    if user is None:
        return render_template("login.html", error="아이디 또는 비밀번호가 틀렸습니다.")

    session["user_id"] = user["user_id"]
    return redirect(url_for("board.list_page"))

@bp.route("/logout")
def logout():
    session.clear()
    return redirect(url_for("board.list_page"))

@bp.route("/login", methods=["POST"])
def login_post():
    user_id = request.form["user_id"]
    user_pw = request.form["user_pw"]

    user = loginUser(user_id, user_pw)

    if user is None:
        return "<script>alert('아이디 또는 비밀번호가 잘못되었습니다'); history.back();</script>"

    session["user"] = user["user_id"]
    session["username"] = user["username"]

    return redirect("/")

