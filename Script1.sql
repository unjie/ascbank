--<ScriptOptions statementTerminator=";"/>

ALTER TABLE "public"."gantt" DROP CONSTRAINT "fk56l1rns7hqabxbevgcxujdo6m";

ALTER TABLE "public"."role_permission" DROP CONSTRAINT "fkjobmrl6dorhlfite4u34hciik";

ALTER TABLE "public"."user_role" DROP CONSTRAINT "fkq5un6x7ecoef5w1n39cop66kl";

ALTER TABLE "public"."gantt" DROP CONSTRAINT "fkblp3s700yq02r86m5im4nk4h9";

ALTER TABLE "public"."navigation" DROP CONSTRAINT "fkk0477gwrac9n2dox90l8f45q3";

ALTER TABLE "public"."menu" DROP CONSTRAINT "fkq8ut9x74iv1mcp4vjgtqgg04i";

ALTER TABLE "public"."article" DROP CONSTRAINT "fk280ltiysaorjb82tibiqquq5q";

ALTER TABLE "public"."menu" DROP CONSTRAINT "fk4paxqyebl0scq6ur9osr0f56k";

ALTER TABLE "public"."user_role" DROP CONSTRAINT "fka9c8iiy6ut0gnx491fqx4pxam";

ALTER TABLE "public"."role_permission" DROP CONSTRAINT "fk90j038mnbnthgkc17mqnoilu9";

ALTER TABLE "public"."user" DROP CONSTRAINT "t_user_pkey";

ALTER TABLE "public"."system_info" DROP CONSTRAINT "t_system_info_pkey";

ALTER TABLE "public"."gantt" DROP CONSTRAINT "t_gantt_pkey";

ALTER TABLE "public"."url_filter" DROP CONSTRAINT "t_url_filter_pkey";

ALTER TABLE "public"."article" DROP CONSTRAINT "t_article_pkey";

ALTER TABLE "public"."role" DROP CONSTRAINT "t_role_pkey";

ALTER TABLE "public"."menu" DROP CONSTRAINT "t_menu_pkey";

ALTER TABLE "public"."shortcut" DROP CONSTRAINT "t_shortcut_pkey";

ALTER TABLE "public"."permission" DROP CONSTRAINT "t_permission_pkey";

ALTER TABLE "public"."navigation" DROP CONSTRAINT "t_navigation_pkey";

DROP INDEX "public"."t_url_filter_pkey";

DROP INDEX "public"."ukln4nygxodh1vmpk5nep24umdx";

DROP INDEX "public"."t_menu_pkey";

DROP INDEX "public"."uk_eggo456jpj5xuej1jpo8b4395";

DROP INDEX "public"."t_shortcut_pkey";

DROP INDEX "public"."uk1jk3ouih7o51iamm4tjtig62k";

DROP INDEX "public"."uk_rnsj7fkgeoob0wy0nlb32j44h";

DROP INDEX "public"."uk_pju8cmo3uddy73n2a8ath3vp3";

DROP INDEX "public"."uk_bojr4duyesjymks0ty5yjwhx3";

DROP INDEX "public"."t_user_pkey";

DROP INDEX "public"."t_system_info_pkey";

DROP INDEX "public"."uk2j13xyyh7chh9gdg1dgvitd32";

DROP INDEX "public"."ukowrvid0ae51dah41gruorxdqi";

DROP INDEX "public"."ukrqhb827itbpcilv0qcfbvm2ep";

DROP INDEX "public"."t_article_pkey";

DROP INDEX "public"."uk_m5bu5erj83eubjsa1nyms0t89";

DROP INDEX "public"."t_gantt_pkey";

DROP INDEX "public"."idx2j13xyyh7chh9gdg1dgvitd32";

DROP INDEX "public"."uknk3llagr5tjnvhmvpomcxohj1";

DROP INDEX "public"."uk_k8dic803e99gi49hvtny416la";

DROP INDEX "public"."idxf4aiyxr8a1fpap7qjr6mg2ha7";

DROP INDEX "public"."t_navigation_pkey";

DROP INDEX "public"."t_role_pkey";

DROP INDEX "public"."uk_jhib4legehrm4yscx9t3lirqi";

DROP INDEX "public"."idxnk3llagr5tjnvhmvpomcxohj1";

DROP INDEX "public"."uk8cr5punfsvecweus4ftuppadx";

DROP INDEX "public"."uk_i6qjjoe560mee5ajdg7v1o6mi";

DROP INDEX "public"."ukpi7i8cjrb75qox4d7wfyfjjpb";

DROP INDEX "public"."t_permission_pkey";

DROP INDEX "public"."ukpju8cmo3uddy73n2a8ath3vp3";

DROP INDEX "public"."uk_gxvxv0ec1k0ogosdolmtu3l2y";

DROP TABLE "public"."article";

DROP TABLE "public"."url_filter";

DROP TABLE "public"."gantt";

DROP TABLE "public"."shortcut";

DROP TABLE "public"."navigation";

DROP TABLE "public"."permission";

DROP TABLE "public"."user_role";

DROP TABLE "public"."role";

DROP TABLE "public"."system_info";

DROP TABLE "public"."user";

DROP TABLE "public"."menu";

DROP TABLE "public"."role_permission";

CREATE TABLE "public"."article" (
		"id" BIGSERIAL DEFAULT nextval('article_id_seq'::regclass) NOT NULL,
		"context" TEXT,
		"menu_id" INT8 NOT NULL,
		"uploadtime" TIMESTAMP DEFAULT now()
	);

CREATE TABLE "public"."url_filter" (
		"id" BIGSERIAL DEFAULT nextval('url_filter_id_seq'::regclass) NOT NULL,
		"name" VARCHAR(86),
		"permissions" VARCHAR(128),
		"roles" VARCHAR(480),
		"sort" INT8,
		"url" VARCHAR(480)
	);

CREATE TABLE "public"."gantt" (
		"id" BIGSERIAL DEFAULT nextval('gantt_id_seq'::regclass) NOT NULL,
		"parent_id" INT8,
		"description" VARCHAR(230),
		"duration" INT2 DEFAULT 0 NOT NULL,
		"expanded" BOOL DEFAULT false,
		"is_live" BOOL DEFAULT false,
		"leaf" BOOL DEFAULT true,
		"progress" FLOAT8 DEFAULT 0,
		"sort" INT2 DEFAULT 20,
		"start_date" TIMESTAMP DEFAULT now(),
		"title" VARCHAR(255) NOT NULL
	);

CREATE TABLE "public"."shortcut" (
		"id" BIGSERIAL DEFAULT nextval('shortcut_id_seq'::regclass) NOT NULL,
		"app_class" VARCHAR(254) NOT NULL,
		"icon_cls" VARCHAR(128) NOT NULL,
		"module" VARCHAR(64) NOT NULL,
		"name" VARCHAR(64) NOT NULL
	);

CREATE TABLE "public"."navigation" (
		"id" BIGSERIAL DEFAULT nextval('navigation_id_seq'::regclass) NOT NULL,
		"alias" VARCHAR(72),
		"description" VARCHAR(220),
		"is_publish" BOOL DEFAULT true,
		"keyword" VARCHAR(120),
		"sort" INT2 DEFAULT 80,
		"stem" VARCHAR(230),
		"style" VARCHAR(36) DEFAULT 'article'::character varying,
		"thumb" VARCHAR(240),
		"title" VARCHAR(60),
		"url" VARCHAR(240),
		"article_id" INT8
	);

CREATE TABLE "public"."permission" (
		"id" BIGSERIAL DEFAULT nextval('permission_id_seq'::regclass) NOT NULL,
		"description" VARCHAR(255),
		"entity_ids" VARCHAR(255),
		"name" VARCHAR(255),
		"permission" VARCHAR(255)
	);

CREATE TABLE "public"."user_role" (
		"user_id" INT8 NOT NULL,
		"role_id" INT8 NOT NULL
	);

CREATE TABLE "public"."role" (
		"id" BIGSERIAL DEFAULT nextval('role_id_seq'::regclass) NOT NULL,
		"description" VARCHAR(360),
		"role_name" VARCHAR(26)
	);

CREATE TABLE "public"."system_info" (
		"id" BIGSERIAL DEFAULT nextval('system_info_id_seq'::regclass) NOT NULL,
		"description" VARCHAR(480),
		"keyname" VARCHAR(90) NOT NULL,
		"organize" VARCHAR(180),
		"type" VARCHAR(255),
		"value" VARCHAR(3000)
	);

CREATE TABLE "public"."user" (
		"id" BIGSERIAL DEFAULT nextval('user_id_seq'::regclass) NOT NULL,
		"avatar" VARCHAR(255),
		"captcha" VARCHAR(4) NOT NULL,
		"description" VARCHAR(255),
		"email" VARCHAR(45),
		"encrypt" VARCHAR(255),
		"is_online" BOOL,
		"lastloginip" VARCHAR(255),
		"lastlogintime" TIMESTAMP,
		"password" VARCHAR(255) NOT NULL,
		"phone" INT8,
		"real_name" VARCHAR(255),
		"regtime" TIMESTAMP,
		"save" BOOL NOT NULL,
		"username" VARCHAR(64) NOT NULL,
		"wechat" VARCHAR(255),
		"wechat_name" VARCHAR(255)
	);

CREATE TABLE "public"."menu" (
		"id" BIGSERIAL DEFAULT nextval('menu_id_seq'::regclass) NOT NULL,
		"parent_id" INT8,
		"alias" VARCHAR(72),
		"author" VARCHAR(32),
		"description" VARCHAR(220),
		"edittime" TIMESTAMP DEFAULT now(),
		"is_navigation" BOOL DEFAULT true,
		"is_publish" BOOL DEFAULT true,
		"keyword" VARCHAR(120),
		"sort" INT2 DEFAULT 80,
		"stem" VARCHAR(230),
		"style" VARCHAR(36) DEFAULT 'page'::character varying NOT NULL,
		"thumb" VARCHAR(240),
		"title" VARCHAR(60) NOT NULL,
		"url" VARCHAR(240),
		"clicks" INT4 DEFAULT 0
	);

CREATE TABLE "public"."role_permission" (
		"role_id" INT8 NOT NULL,
		"permission_id" INT8 NOT NULL
	);

CREATE UNIQUE INDEX "public"."t_url_filter_pkey" ON "public"."url_filter" ("id" ASC);

CREATE UNIQUE INDEX "public"."ukln4nygxodh1vmpk5nep24umdx" ON "public"."role_permission" ("role_id" ASC, "permission_id" ASC);

CREATE UNIQUE INDEX "public"."t_menu_pkey" ON "public"."menu" ("id" ASC);

CREATE UNIQUE INDEX "public"."uk_eggo456jpj5xuej1jpo8b4395" ON "public"."gantt" ("title" ASC);

CREATE UNIQUE INDEX "public"."t_shortcut_pkey" ON "public"."shortcut" ("id" ASC);

CREATE UNIQUE INDEX "public"."uk1jk3ouih7o51iamm4tjtig62k" ON "public"."url_filter" ("id" ASC, "url" ASC);

CREATE UNIQUE INDEX "public"."uk_rnsj7fkgeoob0wy0nlb32j44h" ON "public"."system_info" ("keyname" ASC);

CREATE UNIQUE INDEX "public"."uk_pju8cmo3uddy73n2a8ath3vp3" ON "public"."article" ("menu_id" ASC);

CREATE UNIQUE INDEX "public"."uk_bojr4duyesjymks0ty5yjwhx3" ON "public"."role" ("role_name" ASC);

CREATE UNIQUE INDEX "public"."t_user_pkey" ON "public"."user" ("id" ASC);

CREATE UNIQUE INDEX "public"."t_system_info_pkey" ON "public"."system_info" ("id" ASC);

CREATE UNIQUE INDEX "public"."uk2j13xyyh7chh9gdg1dgvitd32" ON "public"."navigation" ("alias" ASC);

CREATE UNIQUE INDEX "public"."ukowrvid0ae51dah41gruorxdqi" ON "public"."role" ("id" ASC, "role_name" ASC);

CREATE UNIQUE INDEX "public"."ukrqhb827itbpcilv0qcfbvm2ep" ON "public"."user" ("id" ASC, "username" ASC, "email" ASC, "phone" ASC);

CREATE UNIQUE INDEX "public"."t_article_pkey" ON "public"."article" ("id" ASC);

CREATE UNIQUE INDEX "public"."uk_m5bu5erj83eubjsa1nyms0t89" ON "public"."user" ("phone" ASC);

CREATE UNIQUE INDEX "public"."t_gantt_pkey" ON "public"."gantt" ("id" ASC);

CREATE INDEX "public"."idx2j13xyyh7chh9gdg1dgvitd32" ON "public"."navigation" ("alias" ASC);

CREATE UNIQUE INDEX "public"."uknk3llagr5tjnvhmvpomcxohj1" ON "public"."menu" ("alias" ASC);

CREATE UNIQUE INDEX "public"."uk_k8dic803e99gi49hvtny416la" ON "public"."url_filter" ("url" ASC);

CREATE INDEX "public"."idxf4aiyxr8a1fpap7qjr6mg2ha7" ON "public"."menu" ("author" ASC);

CREATE UNIQUE INDEX "public"."t_navigation_pkey" ON "public"."navigation" ("id" ASC);

CREATE UNIQUE INDEX "public"."t_role_pkey" ON "public"."role" ("id" ASC);

CREATE UNIQUE INDEX "public"."uk_jhib4legehrm4yscx9t3lirqi" ON "public"."user" ("username" ASC);

CREATE INDEX "public"."idxnk3llagr5tjnvhmvpomcxohj1" ON "public"."menu" ("alias" ASC);

CREATE UNIQUE INDEX "public"."uk8cr5punfsvecweus4ftuppadx" ON "public"."system_info" ("id" ASC, "keyname" ASC);

CREATE UNIQUE INDEX "public"."uk_i6qjjoe560mee5ajdg7v1o6mi" ON "public"."user" ("email" ASC);

CREATE UNIQUE INDEX "public"."ukpi7i8cjrb75qox4d7wfyfjjpb" ON "public"."user_role" ("user_id" ASC, "role_id" ASC);

CREATE UNIQUE INDEX "public"."t_permission_pkey" ON "public"."permission" ("id" ASC);

CREATE UNIQUE INDEX "public"."ukpju8cmo3uddy73n2a8ath3vp3" ON "public"."article" ("menu_id" ASC);

CREATE UNIQUE INDEX "public"."uk_gxvxv0ec1k0ogosdolmtu3l2y" ON "public"."shortcut" ("module" ASC);

ALTER TABLE "public"."user" ADD CONSTRAINT "t_user_pkey" PRIMARY KEY ("id");

ALTER TABLE "public"."system_info" ADD CONSTRAINT "t_system_info_pkey" PRIMARY KEY ("id");

ALTER TABLE "public"."gantt" ADD CONSTRAINT "t_gantt_pkey" PRIMARY KEY ("id");

ALTER TABLE "public"."url_filter" ADD CONSTRAINT "t_url_filter_pkey" PRIMARY KEY ("id");

ALTER TABLE "public"."article" ADD CONSTRAINT "t_article_pkey" PRIMARY KEY ("id");

ALTER TABLE "public"."role" ADD CONSTRAINT "t_role_pkey" PRIMARY KEY ("id");

ALTER TABLE "public"."menu" ADD CONSTRAINT "t_menu_pkey" PRIMARY KEY ("id");

ALTER TABLE "public"."shortcut" ADD CONSTRAINT "t_shortcut_pkey" PRIMARY KEY ("id");

ALTER TABLE "public"."permission" ADD CONSTRAINT "t_permission_pkey" PRIMARY KEY ("id");

ALTER TABLE "public"."navigation" ADD CONSTRAINT "t_navigation_pkey" PRIMARY KEY ("id");

ALTER TABLE "public"."gantt" ADD CONSTRAINT "fk56l1rns7hqabxbevgcxujdo6m" FOREIGN KEY ("parent_id")
	REFERENCES "public"."gantt" ("id");

ALTER TABLE "public"."role_permission" ADD CONSTRAINT "fkjobmrl6dorhlfite4u34hciik" FOREIGN KEY ("permission_id")
	REFERENCES "public"."permission" ("id");

ALTER TABLE "public"."user_role" ADD CONSTRAINT "fkq5un6x7ecoef5w1n39cop66kl" FOREIGN KEY ("user_id")
	REFERENCES "public"."user" ("id");

ALTER TABLE "public"."gantt" ADD CONSTRAINT "fkblp3s700yq02r86m5im4nk4h9" FOREIGN KEY ("parent_id")
	REFERENCES "public"."gantt" ("id");

ALTER TABLE "public"."navigation" ADD CONSTRAINT "fkk0477gwrac9n2dox90l8f45q3" FOREIGN KEY ("article_id")
	REFERENCES "public"."article" ("id");

ALTER TABLE "public"."menu" ADD CONSTRAINT "fkq8ut9x74iv1mcp4vjgtqgg04i" FOREIGN KEY ("parent_id")
	REFERENCES "public"."menu" ("id");

ALTER TABLE "public"."article" ADD CONSTRAINT "fk280ltiysaorjb82tibiqquq5q" FOREIGN KEY ("menu_id")
	REFERENCES "public"."menu" ("id");

ALTER TABLE "public"."menu" ADD CONSTRAINT "fk4paxqyebl0scq6ur9osr0f56k" FOREIGN KEY ("parent_id")
	REFERENCES "public"."menu" ("id");

ALTER TABLE "public"."user_role" ADD CONSTRAINT "fka9c8iiy6ut0gnx491fqx4pxam" FOREIGN KEY ("role_id")
	REFERENCES "public"."role" ("id");

ALTER TABLE "public"."role_permission" ADD CONSTRAINT "fk90j038mnbnthgkc17mqnoilu9" FOREIGN KEY ("role_id")
	REFERENCES "public"."role" ("id");

