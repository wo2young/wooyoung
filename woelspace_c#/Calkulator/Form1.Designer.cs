namespace Calkulator
{
    partial class Form1
    {
        /// <summary>
        /// 필수 디자이너 변수입니다.
        /// </summary>
        private System.ComponentModel.IContainer components = null;

        /// <summary>
        /// 사용 중인 모든 리소스를 정리합니다.
        /// </summary>
        /// <param name="disposing">관리되는 리소스를 삭제해야 하면 true이고, 그렇지 않으면 false입니다.</param>
        protected override void Dispose(bool disposing)
        {
            if (disposing && (components != null))
            {
                components.Dispose();
            }
            base.Dispose(disposing);
        }

        #region Windows Form 디자이너에서 생성한 코드

        /// <summary>
        /// 디자이너 지원에 필요한 메서드입니다. 
        /// 이 메서드의 내용을 코드 편집기로 수정하지 마세요.
        /// </summary>
        private void InitializeComponent()
        {
            this.label1 = new System.Windows.Forms.Label();
            this.tableLayoutPanel1 = new System.Windows.Forms.TableLayoutPanel();
            this.btn_eq = new System.Windows.Forms.Button();
            this.btn0 = new System.Windows.Forms.Button();
            this.btn2 = new System.Windows.Forms.Button();
            this.btn7 = new System.Windows.Forms.Button();
            this.btn8 = new System.Windows.Forms.Button();
            this.btn9 = new System.Windows.Forms.Button();
            this.btn_dv = new System.Windows.Forms.Button();
            this.btn_mul = new System.Windows.Forms.Button();
            this.btn6 = new System.Windows.Forms.Button();
            this.btn5 = new System.Windows.Forms.Button();
            this.btn4 = new System.Windows.Forms.Button();
            this.btn3 = new System.Windows.Forms.Button();
            this.btn_mi = new System.Windows.Forms.Button();
            this.btn_pl = new System.Windows.Forms.Button();
            this.btn_cl = new System.Windows.Forms.Button();
            this.btn1 = new System.Windows.Forms.Button();
            this.tableLayoutPanel1.SuspendLayout();
            this.SuspendLayout();
            // 
            // label1
            // 
            this.label1.Dock = System.Windows.Forms.DockStyle.Top;
            this.label1.Font = new System.Drawing.Font("굴림", 48F, System.Drawing.FontStyle.Bold);
            this.label1.Location = new System.Drawing.Point(0, 0);
            this.label1.Name = "label1";
            this.label1.Size = new System.Drawing.Size(400, 97);
            this.label1.TabIndex = 0;
            this.label1.Text = "0";
            this.label1.TextAlign = System.Drawing.ContentAlignment.MiddleRight;
            // 
            // tableLayoutPanel1
            // 
            this.tableLayoutPanel1.ColumnCount = 4;
            this.tableLayoutPanel1.ColumnStyles.Add(new System.Windows.Forms.ColumnStyle(System.Windows.Forms.SizeType.Percent, 25F));
            this.tableLayoutPanel1.ColumnStyles.Add(new System.Windows.Forms.ColumnStyle(System.Windows.Forms.SizeType.Percent, 25F));
            this.tableLayoutPanel1.ColumnStyles.Add(new System.Windows.Forms.ColumnStyle(System.Windows.Forms.SizeType.Percent, 25F));
            this.tableLayoutPanel1.ColumnStyles.Add(new System.Windows.Forms.ColumnStyle(System.Windows.Forms.SizeType.Percent, 25F));
            this.tableLayoutPanel1.Controls.Add(this.btn_eq, 2, 3);
            this.tableLayoutPanel1.Controls.Add(this.btn0, 1, 3);
            this.tableLayoutPanel1.Controls.Add(this.btn2, 1, 2);
            this.tableLayoutPanel1.Controls.Add(this.btn7, 0, 0);
            this.tableLayoutPanel1.Controls.Add(this.btn8, 1, 0);
            this.tableLayoutPanel1.Controls.Add(this.btn9, 2, 0);
            this.tableLayoutPanel1.Controls.Add(this.btn_dv, 3, 0);
            this.tableLayoutPanel1.Controls.Add(this.btn_mul, 3, 1);
            this.tableLayoutPanel1.Controls.Add(this.btn6, 2, 1);
            this.tableLayoutPanel1.Controls.Add(this.btn5, 1, 1);
            this.tableLayoutPanel1.Controls.Add(this.btn4, 0, 1);
            this.tableLayoutPanel1.Controls.Add(this.btn3, 2, 2);
            this.tableLayoutPanel1.Controls.Add(this.btn_mi, 3, 2);
            this.tableLayoutPanel1.Controls.Add(this.btn_pl, 3, 3);
            this.tableLayoutPanel1.Controls.Add(this.btn_cl, 0, 3);
            this.tableLayoutPanel1.Controls.Add(this.btn1, 0, 2);
            this.tableLayoutPanel1.Dock = System.Windows.Forms.DockStyle.Fill;
            this.tableLayoutPanel1.Font = new System.Drawing.Font("굴림", 30F);
            this.tableLayoutPanel1.Location = new System.Drawing.Point(0, 97);
            this.tableLayoutPanel1.Name = "tableLayoutPanel1";
            this.tableLayoutPanel1.RowCount = 4;
            this.tableLayoutPanel1.RowStyles.Add(new System.Windows.Forms.RowStyle(System.Windows.Forms.SizeType.Percent, 25F));
            this.tableLayoutPanel1.RowStyles.Add(new System.Windows.Forms.RowStyle(System.Windows.Forms.SizeType.Percent, 25F));
            this.tableLayoutPanel1.RowStyles.Add(new System.Windows.Forms.RowStyle(System.Windows.Forms.SizeType.Percent, 25F));
            this.tableLayoutPanel1.RowStyles.Add(new System.Windows.Forms.RowStyle(System.Windows.Forms.SizeType.Percent, 25F));
            this.tableLayoutPanel1.Size = new System.Drawing.Size(400, 484);
            this.tableLayoutPanel1.TabIndex = 1;
            // 
            // btn_eq
            // 
            this.btn_eq.Font = new System.Drawing.Font("굴림", 35F, System.Drawing.FontStyle.Bold);
            this.btn_eq.Location = new System.Drawing.Point(203, 366);
            this.btn_eq.Name = "btn_eq";
            this.btn_eq.Size = new System.Drawing.Size(94, 115);
            this.btn_eq.TabIndex = 6;
            this.btn_eq.Text = "=";
            this.btn_eq.UseVisualStyleBackColor = true;
            this.btn_eq.Click += new System.EventHandler(this.btn_eq_Click);
            // 
            // btn0
            // 
            this.btn0.Font = new System.Drawing.Font("굴림", 35F, System.Drawing.FontStyle.Bold);
            this.btn0.Location = new System.Drawing.Point(103, 366);
            this.btn0.Name = "btn0";
            this.btn0.Size = new System.Drawing.Size(94, 115);
            this.btn0.TabIndex = 5;
            this.btn0.Text = "0";
            this.btn0.UseVisualStyleBackColor = true;
            this.btn0.Click += new System.EventHandler(this.btn0_Click);
            // 
            // btn2
            // 
            this.btn2.Font = new System.Drawing.Font("굴림", 35.25F, System.Drawing.FontStyle.Bold, System.Drawing.GraphicsUnit.Point, ((byte)(129)));
            this.btn2.Location = new System.Drawing.Point(103, 245);
            this.btn2.Name = "btn2";
            this.btn2.Size = new System.Drawing.Size(94, 115);
            this.btn2.TabIndex = 3;
            this.btn2.Text = "2";
            this.btn2.Click += new System.EventHandler(this.btn2_Click);
            // 
            // btn7
            // 
            this.btn7.Font = new System.Drawing.Font("굴림", 35F, System.Drawing.FontStyle.Bold);
            this.btn7.Location = new System.Drawing.Point(3, 3);
            this.btn7.Name = "btn7";
            this.btn7.Size = new System.Drawing.Size(94, 115);
            this.btn7.TabIndex = 0;
            this.btn7.Text = "7";
            this.btn7.UseVisualStyleBackColor = true;
            this.btn7.Click += new System.EventHandler(this.btn7_Click);
            // 
            // btn8
            // 
            this.btn8.Font = new System.Drawing.Font("굴림", 35F, System.Drawing.FontStyle.Bold);
            this.btn8.Location = new System.Drawing.Point(103, 3);
            this.btn8.Name = "btn8";
            this.btn8.Size = new System.Drawing.Size(94, 115);
            this.btn8.TabIndex = 1;
            this.btn8.Text = "8";
            this.btn8.UseVisualStyleBackColor = true;
            this.btn8.Click += new System.EventHandler(this.btn8_Click);
            // 
            // btn9
            // 
            this.btn9.Font = new System.Drawing.Font("굴림", 35F, System.Drawing.FontStyle.Bold);
            this.btn9.Location = new System.Drawing.Point(203, 3);
            this.btn9.Name = "btn9";
            this.btn9.Size = new System.Drawing.Size(94, 115);
            this.btn9.TabIndex = 2;
            this.btn9.Text = "9";
            this.btn9.UseVisualStyleBackColor = true;
            this.btn9.Click += new System.EventHandler(this.btn9_Click);
            // 
            // btn_dv
            // 
            this.btn_dv.Font = new System.Drawing.Font("굴림", 35F, System.Drawing.FontStyle.Bold);
            this.btn_dv.Location = new System.Drawing.Point(303, 3);
            this.btn_dv.Name = "btn_dv";
            this.btn_dv.Size = new System.Drawing.Size(94, 115);
            this.btn_dv.TabIndex = 2;
            this.btn_dv.Text = "/";
            this.btn_dv.UseVisualStyleBackColor = true;
            this.btn_dv.Click += new System.EventHandler(this.btn_dv_Click);
            // 
            // btn_mul
            // 
            this.btn_mul.Font = new System.Drawing.Font("굴림", 35F, System.Drawing.FontStyle.Bold);
            this.btn_mul.Location = new System.Drawing.Point(303, 124);
            this.btn_mul.Name = "btn_mul";
            this.btn_mul.Size = new System.Drawing.Size(94, 115);
            this.btn_mul.TabIndex = 2;
            this.btn_mul.Text = "*";
            this.btn_mul.UseVisualStyleBackColor = true;
            this.btn_mul.Click += new System.EventHandler(this.btn_mul_Click);
            // 
            // btn6
            // 
            this.btn6.Font = new System.Drawing.Font("굴림", 35F, System.Drawing.FontStyle.Bold);
            this.btn6.Location = new System.Drawing.Point(203, 124);
            this.btn6.Name = "btn6";
            this.btn6.Size = new System.Drawing.Size(94, 115);
            this.btn6.TabIndex = 2;
            this.btn6.Text = "6";
            this.btn6.UseVisualStyleBackColor = true;
            this.btn6.Click += new System.EventHandler(this.btn6_Click);
            // 
            // btn5
            // 
            this.btn5.Font = new System.Drawing.Font("굴림", 35F, System.Drawing.FontStyle.Bold);
            this.btn5.Location = new System.Drawing.Point(103, 124);
            this.btn5.Name = "btn5";
            this.btn5.Size = new System.Drawing.Size(94, 115);
            this.btn5.TabIndex = 2;
            this.btn5.Text = "5";
            this.btn5.UseVisualStyleBackColor = true;
            this.btn5.Click += new System.EventHandler(this.btn5_Click);
            // 
            // btn4
            // 
            this.btn4.Font = new System.Drawing.Font("굴림", 35F, System.Drawing.FontStyle.Bold);
            this.btn4.Location = new System.Drawing.Point(3, 124);
            this.btn4.Name = "btn4";
            this.btn4.Size = new System.Drawing.Size(94, 115);
            this.btn4.TabIndex = 2;
            this.btn4.Text = "4";
            this.btn4.UseVisualStyleBackColor = true;
            this.btn4.Click += new System.EventHandler(this.btn4_Click);
            // 
            // btn3
            // 
            this.btn3.Font = new System.Drawing.Font("굴림", 35F, System.Drawing.FontStyle.Bold);
            this.btn3.Location = new System.Drawing.Point(203, 245);
            this.btn3.Name = "btn3";
            this.btn3.Size = new System.Drawing.Size(94, 115);
            this.btn3.TabIndex = 2;
            this.btn3.Text = "3";
            this.btn3.UseVisualStyleBackColor = true;
            this.btn3.Click += new System.EventHandler(this.btn3_Click);
            // 
            // btn_mi
            // 
            this.btn_mi.Font = new System.Drawing.Font("굴림", 35F, System.Drawing.FontStyle.Bold);
            this.btn_mi.Location = new System.Drawing.Point(303, 245);
            this.btn_mi.Name = "btn_mi";
            this.btn_mi.Size = new System.Drawing.Size(94, 115);
            this.btn_mi.TabIndex = 2;
            this.btn_mi.Text = "-";
            this.btn_mi.UseVisualStyleBackColor = true;
            this.btn_mi.Click += new System.EventHandler(this.btn_mi_Click);
            // 
            // btn_pl
            // 
            this.btn_pl.Font = new System.Drawing.Font("굴림", 35F, System.Drawing.FontStyle.Bold);
            this.btn_pl.Location = new System.Drawing.Point(303, 366);
            this.btn_pl.Name = "btn_pl";
            this.btn_pl.Size = new System.Drawing.Size(94, 115);
            this.btn_pl.TabIndex = 2;
            this.btn_pl.Text = "+";
            this.btn_pl.UseVisualStyleBackColor = true;
            this.btn_pl.Click += new System.EventHandler(this.btn_pl_Click);
            // 
            // btn_cl
            // 
            this.btn_cl.Font = new System.Drawing.Font("굴림", 35F, System.Drawing.FontStyle.Bold);
            this.btn_cl.Location = new System.Drawing.Point(3, 366);
            this.btn_cl.Name = "btn_cl";
            this.btn_cl.Size = new System.Drawing.Size(94, 115);
            this.btn_cl.TabIndex = 2;
            this.btn_cl.Text = "C";
            this.btn_cl.UseVisualStyleBackColor = true;
            this.btn_cl.Click += new System.EventHandler(this.btn_cl_Click);
            // 
            // btn1
            // 
            this.btn1.Font = new System.Drawing.Font("굴림", 35.25F, System.Drawing.FontStyle.Bold, System.Drawing.GraphicsUnit.Point, ((byte)(129)));
            this.btn1.Location = new System.Drawing.Point(3, 245);
            this.btn1.Name = "btn1";
            this.btn1.Size = new System.Drawing.Size(94, 115);
            this.btn1.TabIndex = 4;
            this.btn1.Text = "1";
            this.btn1.Click += new System.EventHandler(this.btn1_Click);
            // 
            // Form1
            // 
            this.AutoScaleDimensions = new System.Drawing.SizeF(7F, 12F);
            this.AutoScaleMode = System.Windows.Forms.AutoScaleMode.Font;
            this.ClientSize = new System.Drawing.Size(400, 581);
            this.Controls.Add(this.tableLayoutPanel1);
            this.Controls.Add(this.label1);
            this.Name = "Form1";
            this.Text = "Form1";
            this.tableLayoutPanel1.ResumeLayout(false);
            this.ResumeLayout(false);

        }

        #endregion

        private System.Windows.Forms.Label label1;
        private System.Windows.Forms.TableLayoutPanel tableLayoutPanel1;
        private System.Windows.Forms.Button btn7;
        private System.Windows.Forms.Button btn8;
        private System.Windows.Forms.Button btn9;
        private System.Windows.Forms.Button btn_dv;
        private System.Windows.Forms.Button btn_mul;
        private System.Windows.Forms.Button btn6;
        private System.Windows.Forms.Button btn5;
        private System.Windows.Forms.Button btn4;
        private System.Windows.Forms.Button btn1;
        private System.Windows.Forms.Button btn2;
        private System.Windows.Forms.Button btn3;
        private System.Windows.Forms.Button btn_mi;
        private System.Windows.Forms.Button btn_pl;
        private System.Windows.Forms.Button btn_cl;
        private System.Windows.Forms.Button btn_eq;
        private System.Windows.Forms.Button btn0;
    }
}

