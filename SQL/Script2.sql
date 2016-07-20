--<ScriptOptions statementTerminator=";"/>
/*
ALTER TABLE "public"."t_gantt" DROP CONSTRAINT "fkblp3s700yq02r86m5im4nk4h9";

ALTER TABLE "public"."t_menu" DROP CONSTRAINT "fk4paxqyebl0scq6ur9osr0f56k";

ALTER TABLE "public"."t_menu" DROP CONSTRAINT "fkq8ut9x74iv1mcp4vjgtqgg04i";

ALTER TABLE "public"."t_role_permission" DROP CONSTRAINT "fk90j038mnbnthgkc17mqnoilu9";

ALTER TABLE "public"."t_article" DROP CONSTRAINT "fk280ltiysaorjb82tibiqquq5q";

ALTER TABLE "public"."t_gantt" DROP CONSTRAINT "fk56l1rns7hqabxbevgcxujdo6m";

ALTER TABLE "public"."t_user_permission" DROP CONSTRAINT "t_user_permission_permission_id_fkey";

ALTER TABLE "public"."t_role_permission" DROP CONSTRAINT "fkjobmrl6dorhlfite4u34hciik";

ALTER TABLE "public"."t_user_permission" DROP CONSTRAINT "t_user_permission_user_id_fkey";

ALTER TABLE "public"."t_user_role" DROP CONSTRAINT "fkq5un6x7ecoef5w1n39cop66kl";

ALTER TABLE "public"."t_user_role" DROP CONSTRAINT "fka9c8iiy6ut0gnx491fqx4pxam";

ALTER TABLE "public"."t_navigation" DROP CONSTRAINT "fkk0477gwrac9n2dox90l8f45q3";

ALTER TABLE "public"."t_permission" DROP CONSTRAINT "t_permission_pkey";

ALTER TABLE "public"."t_user" DROP CONSTRAINT "t_user_pkey";

ALTER TABLE "public"."t_role" DROP CONSTRAINT "t_role_pkey";

ALTER TABLE "public"."t_url_filter" DROP CONSTRAINT "t_url_filter_pkey";

ALTER TABLE "public"."t_shortcut" DROP CONSTRAINT "t_shortcut_pkey";

ALTER TABLE "public"."t_article" DROP CONSTRAINT "t_article_pkey";

ALTER TABLE "public"."t_gantt" DROP CONSTRAINT "t_gantt_pkey";

ALTER TABLE "public"."t_system_info" DROP CONSTRAINT "t_system_info_pkey";

ALTER TABLE "public"."t_menu" DROP CONSTRAINT "t_menu_pkey";

ALTER TABLE "public"."t_navigation" DROP CONSTRAINT "t_navigation_pkey";

DROP INDEX "public"."t_menu_pkey";

DROP INDEX "pg_catalog"."pg_opfamily_oid_index";

DROP INDEX "pg_catalog"."pg_amop_fam_strat_index";

DROP INDEX "public"."ukowrvid0ae51dah41gruorxdqi";

DROP INDEX "pg_catalog"."pg_attribute_relid_attnam_index";

DROP INDEX "public"."ukln4nygxodh1vmpk5nep24umdx";

DROP INDEX "pg_catalog"."pg_operator_oprname_l_r_n_index";

DROP INDEX "pg_catalog"."pg_index_indexrelid_index";

DROP INDEX "pg_catalog"."pg_constraint_contypid_index";

DROP INDEX "pg_catalog"."pg_rewrite_rel_rulename_index";

DROP INDEX "pg_catalog"."pg_class_tblspc_relfilenode_index";

DROP INDEX "public"."uk1jk3ouih7o51iamm4tjtig62k";

DROP INDEX "pg_catalog"."pg_depend_depender_index";

DROP INDEX "public"."uk2j13xyyh7chh9gdg1dgvitd32";

DROP INDEX "pg_catalog"."pg_trigger_tgrelid_tgname_index";

DROP INDEX "pg_catalog"."pg_language_oid_index";

DROP INDEX "pg_catalog"."pg_amop_opr_fam_index";

DROP INDEX "pg_catalog"."pg_type_oid_index";

DROP INDEX "pg_catalog"."pg_ts_parser_prsname_index";

DROP INDEX "pg_catalog"."pg_user_mapping_oid_index";

DROP INDEX "pg_catalog"."pg_amproc_fam_proc_index";

DROP INDEX "pg_catalog"."pg_collation_oid_index";

DROP INDEX "pg_catalog"."pg_ts_template_oid_index";

DROP INDEX "pg_catalog"."pg_attrdef_oid_index";

DROP INDEX "pg_catalog"."pg_ts_template_tmplname_index";

DROP INDEX "public"."uknk3llagr5tjnvhmvpomcxohj1";

DROP INDEX "pg_catalog"."pg_shdepend_depender_index";

DROP INDEX "public"."t_navigation_pkey";

DROP INDEX "pg_catalog"."pg_amproc_oid_index";

DROP INDEX "pg_catalog"."pg_enum_oid_index";

DROP INDEX "pg_catalog"."pg_constraint_conname_nsp_index";

DROP INDEX "pg_catalog"."pg_aggregate_fnoid_index";

DROP INDEX "public"."t_article_pkey";

DROP INDEX "public"."t_user_pkey";

DROP INDEX "public"."ukpju8cmo3uddy73n2a8ath3vp3";

DROP INDEX "public"."t_shortcut_pkey";

DROP INDEX "public"."uk_jhib4legehrm4yscx9t3lirqi";

DROP INDEX "pg_catalog"."pg_namespace_oid_index";

DROP INDEX "pg_catalog"."pg_foreign_table_relid_index";

DROP INDEX "public"."uk_rnsj7fkgeoob0wy0nlb32j44h";

DROP INDEX "pg_catalog"."pg_replication_origin_roname_index";

DROP INDEX "pg_catalog"."pg_trigger_oid_index";

DROP INDEX "pg_catalog"."pg_opfamily_am_name_nsp_index";

DROP INDEX "pg_catalog"."pg_ts_parser_oid_index";

DROP INDEX "pg_catalog"."pg_shdescription_o_c_index";

DROP INDEX "public"."t_url_filter_pkey";

DROP INDEX "public"."uk8cr5punfsvecweus4ftuppadx";

DROP INDEX "pg_catalog"."pg_class_oid_index";

DROP INDEX "pg_catalog"."pg_opclass_am_name_nsp_index";

DROP INDEX "pg_catalog"."pg_shseclabel_object_index";

DROP INDEX "public"."idx2j13xyyh7chh9gdg1dgvitd32";

DROP INDEX "pg_catalog"."pg_enum_typid_sortorder_index";

DROP INDEX "pg_catalog"."pg_depend_reference_index";

DROP INDEX "pg_catalog"."pg_replication_origin_roiident_index";

DROP INDEX "pg_catalog"."pg_pltemplate_name_index";

DROP INDEX "pg_catalog"."pg_cast_oid_index";

DROP INDEX "public"."t_permission_pkey";

DROP INDEX "public"."uk_bojr4duyesjymks0ty5yjwhx3";

DROP INDEX "pg_catalog"."pg_type_typname_nsp_index";

DROP INDEX "pg_catalog"."pg_constraint_conrelid_index";

DROP INDEX "pg_catalog"."pg_language_name_index";

DROP INDEX "pg_catalog"."pg_proc_proname_args_nsp_index";

DROP INDEX "pg_catalog"."pg_inherits_relid_seqno_index";

DROP INDEX "pg_catalog"."pg_event_trigger_oid_index";

DROP INDEX "pg_catalog"."pg_description_o_c_o_index";

DROP INDEX "public"."ukrqhb827itbpcilv0qcfbvm2ep";

DROP INDEX "public"."t_gantt_pkey";

DROP INDEX "pg_catalog"."pg_shdepend_reference_index";

DROP INDEX "pg_catalog"."pg_ts_config_cfgname_index";

DROP INDEX "pg_catalog"."pg_ts_config_map_index";

DROP INDEX "pg_catalog"."pg_namespace_nspname_index";

DROP INDEX "public"."uk_i6qjjoe560mee5ajdg7v1o6mi";

DROP INDEX "pg_catalog"."pg_conversion_default_index";

DROP INDEX "pg_catalog"."pg_foreign_data_wrapper_name_index";

DROP INDEX "pg_catalog"."pg_seclabel_object_index";

DROP INDEX "public"."uk_gxvxv0ec1k0ogosdolmtu3l2y";

DROP INDEX "pg_catalog"."pg_attrdef_adrelid_adnum_index";

DROP INDEX "pg_catalog"."pg_index_indrelid_index";

DROP INDEX "pg_catalog"."pg_class_relname_nsp_index";

DROP INDEX "pg_catalog"."pg_largeobject_metadata_oid_index";

DROP INDEX "public"."t_system_info_pkey";

DROP INDEX "pg_catalog"."pg_database_oid_index";

DROP INDEX "pg_catalog"."pg_event_trigger_evtname_index";

DROP INDEX "pg_catalog"."pg_inherits_parent_index";

DROP INDEX "pg_catalog"."pg_ts_dict_oid_index";

DROP INDEX "pg_catalog"."pg_default_acl_oid_index";

DROP INDEX "pg_catalog"."pg_conversion_oid_index";

DROP INDEX "pg_catalog"."pg_foreign_server_oid_index";

DROP INDEX "public"."uk_k8dic803e99gi49hvtny416la";

DROP INDEX "pg_catalog"."pg_trigger_tgconstraint_index";

DROP INDEX "pg_catalog"."pg_user_mapping_user_server_index";

DROP INDEX "pg_catalog"."pg_default_acl_role_nsp_obj_index";

DROP INDEX "pg_catalog"."pg_authid_rolname_index";

DROP INDEX "pg_catalog"."pg_policy_oid_index";

DROP INDEX "public"."uk_pju8cmo3uddy73n2a8ath3vp3";

DROP INDEX "pg_catalog"."pg_am_oid_index";

DROP INDEX "public"."uk_eggo456jpj5xuej1jpo8b4395";

DROP INDEX "pg_catalog"."pg_largeobject_loid_pn_index";

DROP INDEX "pg_catalog"."pg_extension_oid_index";

DROP INDEX "pg_catalog"."pg_auth_members_member_role_index";

DROP INDEX "pg_catalog"."pg_conversion_name_nsp_index";

DROP INDEX "pg_catalog"."pg_db_role_setting_databaseid_rol_index";

DROP INDEX "pg_catalog"."pg_attribute_relid_attnum_index";

DROP INDEX "pg_catalog"."pg_am_name_index";

DROP INDEX "pg_catalog"."pg_foreign_data_wrapper_oid_index";

DROP INDEX "pg_catalog"."pg_constraint_oid_index";

DROP INDEX "pg_catalog"."pg_ts_dict_dictname_index";

DROP INDEX "public"."ukpi7i8cjrb75qox4d7wfyfjjpb";

DROP INDEX "public"."t_role_pkey";

DROP INDEX "pg_catalog"."pg_proc_oid_index";

DROP INDEX "pg_catalog"."pg_amop_oid_index";

DROP INDEX "public"."idxnk3llagr5tjnvhmvpomcxohj1";

DROP INDEX "pg_catalog"."pg_extension_name_index";

DROP INDEX "pg_catalog"."pg_foreign_server_name_index";

DROP INDEX "pg_catalog"."pg_rewrite_oid_index";

DROP INDEX "pg_catalog"."pg_ts_config_oid_index";

DROP INDEX "pg_catalog"."pg_operator_oid_index";

DROP INDEX "pg_catalog"."pg_tablespace_oid_index";

DROP INDEX "pg_catalog"."pg_statistic_relid_att_inh_index";

DROP INDEX "public"."uk_m5bu5erj83eubjsa1nyms0t89";

DROP INDEX "pg_catalog"."pg_database_datname_index";

DROP INDEX "pg_catalog"."pg_cast_source_target_index";

DROP INDEX "pg_catalog"."pg_transform_type_lang_index";

DROP INDEX "pg_catalog"."pg_tablespace_spcname_index";

DROP INDEX "pg_catalog"."pg_transform_oid_index";

DROP INDEX "pg_catalog"."pg_enum_typid_label_index";

DROP INDEX "pg_catalog"."pg_auth_members_role_member_index";

DROP INDEX "pg_catalog"."pg_opclass_oid_index";

DROP INDEX "public"."idxf4aiyxr8a1fpap7qjr6mg2ha7";

DROP INDEX "pg_catalog"."pg_authid_oid_index";

DROP INDEX "pg_catalog"."pg_policy_polrelid_polname_index";

DROP INDEX "pg_catalog"."pg_range_rngtypid_index";

DROP INDEX "pg_catalog"."pg_collation_name_enc_nsp_index";

DROP TABLE "pg_catalog"."pg_foreign_table";

DROP TABLE "pg_catalog"."pg_index";

DROP TABLE "pg_catalog"."pg_range";

DROP TABLE "pg_catalog"."pg_constraint";

DROP TABLE "pg_catalog"."pg_amproc";

DROP TABLE "public"."t_permission";

DROP TABLE "pg_catalog"."pg_rewrite";

DROP TABLE "pg_catalog"."pg_replication_origin";

DROP TABLE "pg_catalog"."pg_aggregate";

DROP TABLE "pg_catalog"."pg_auth_members";

DROP TABLE "public"."t_menu";

DROP TABLE "pg_catalog"."pg_enum";

DROP TABLE "pg_catalog"."pg_conversion";

DROP TABLE "pg_catalog"."pg_shdepend";

DROP TABLE "pg_catalog"."pg_foreign_data_wrapper";

DROP TABLE "pg_catalog"."pg_attribute";

DROP TABLE "pg_catalog"."pg_proc";

DROP TABLE "pg_catalog"."pg_transform";

DROP TABLE "pg_catalog"."pg_opclass";

DROP TABLE "pg_catalog"."pg_seclabel";

DROP TABLE "pg_catalog"."pg_type";

DROP TABLE "public"."t_role_permission";

DROP TABLE "pg_catalog"."pg_description";

DROP TABLE "pg_catalog"."pg_default_acl";

DROP TABLE "public"."t_role";

DROP TABLE "pg_catalog"."pg_amop";

DROP TABLE "pg_catalog"."pg_authid";

DROP TABLE "pg_catalog"."pg_namespace";

DROP TABLE "pg_catalog"."pg_largeobject";

DROP TABLE "pg_catalog"."pg_shdescription";

DROP TABLE "pg_catalog"."pg_ts_template";

DROP TABLE "pg_catalog"."pg_pltemplate";

DROP TABLE "pg_catalog"."pg_shseclabel";

DROP TABLE "pg_catalog"."pg_am";

DROP TABLE "pg_catalog"."pg_largeobject_metadata";

DROP TABLE "pg_catalog"."pg_opfamily";

DROP TABLE "pg_catalog"."pg_ts_config_map";

DROP TABLE "public"."t_user";

DROP TABLE "pg_catalog"."pg_extension";

DROP TABLE "pg_catalog"."pg_collation";

DROP TABLE "pg_catalog"."pg_foreign_server";

DROP TABLE "public"."t_system_info";

DROP TABLE "pg_catalog"."pg_language";

DROP TABLE "pg_catalog"."pg_depend";

DROP TABLE "pg_catalog"."pg_ts_config";

DROP TABLE "pg_catalog"."pg_ts_parser";

DROP TABLE "pg_catalog"."pg_class";

DROP TABLE "pg_catalog"."pg_cast";

DROP TABLE "pg_catalog"."pg_tablespace";

DROP TABLE "pg_catalog"."pg_statistic";

DROP TABLE "pg_catalog"."pg_event_trigger";

DROP TABLE "public"."t_navigation";

DROP TABLE "pg_catalog"."pg_inherits";

DROP TABLE "pg_catalog"."pg_trigger";

DROP TABLE "public"."t_article";

DROP TABLE "public"."t_gantt";

DROP TABLE "pg_catalog"."pg_attrdef";

DROP TABLE "public"."t_user_permission";

DROP TABLE "public"."t_url_filter";

DROP TABLE "public"."t_shortcut";

DROP TABLE "pg_catalog"."pg_operator";

DROP TABLE "pg_catalog"."pg_db_role_setting";

DROP TABLE "pg_catalog"."pg_ts_dict";

DROP TABLE "pg_catalog"."pg_user_mapping";

DROP TABLE "public"."t_user_role";

DROP TABLE "pg_catalog"."pg_policy";

DROP TABLE "pg_catalog"."pg_database";
*/


CREATE TABLE "pg_catalog"."pg_foreign_table" (
		"ftrelid" OID NOT NULL,
		"ftserver" OID NOT NULL,
		"ftoptions" TEXT[ ]
	);

CREATE TABLE "pg_catalog"."pg_index" (
		"indexrelid" OID NOT NULL,
		"indrelid" OID NOT NULL,
		"indnatts" INT2 NOT NULL,
		"indisunique" BOOL NOT NULL,
		"indisprimary" BOOL NOT NULL,
		"indisexclusion" BOOL NOT NULL,
		"indimmediate" BOOL NOT NULL,
		"indisclustered" BOOL NOT NULL,
		"indisvalid" BOOL NOT NULL,
		"indcheckxmin" BOOL NOT NULL,
		"indisready" BOOL NOT NULL,
		"indislive" BOOL NOT NULL,
		"indisreplident" BOOL NOT NULL,
		"indkey" null NOT NULL,
		"indcollation" null NOT NULL,
		"indclass" null NOT NULL,
		"indoption" null NOT NULL,
		"indexprs" null,
		"indpred" null
	);

CREATE TABLE "pg_catalog"."pg_range" (
		"rngtypid" OID NOT NULL,
		"rngsubtype" OID NOT NULL,
		"rngcollation" OID NOT NULL,
		"rngsubopc" OID NOT NULL,
		"rngcanonical" REGPROC NOT NULL,
		"rngsubdiff" REGPROC NOT NULL
	);

CREATE TABLE "pg_catalog"."pg_constraint" (
		"conname" CIDR(64) NOT NULL,
		"connamespace" OID NOT NULL,
		"contype" null NOT NULL,
		"condeferrable" BOOL NOT NULL,
		"condeferred" BOOL NOT NULL,
		"convalidated" BOOL NOT NULL,
		"conrelid" OID NOT NULL,
		"contypid" OID NOT NULL,
		"conindid" OID NOT NULL,
		"confrelid" OID NOT NULL,
		"confupdtype" null NOT NULL,
		"confdeltype" null NOT NULL,
		"confmatchtype" null NOT NULL,
		"conislocal" BOOL NOT NULL,
		"coninhcount" INT4 NOT NULL,
		"connoinherit" BOOL NOT NULL,
		"conkey" INT2[ ],
		"confkey" INT2[ ],
		"conpfeqop" OID[ ],
		"conppeqop" OID[ ],
		"conffeqop" OID[ ],
		"conexclop" OID[ ],
		"conbin" null,
		"consrc" TEXT
	);

CREATE TABLE "pg_catalog"."pg_amproc" (
		"amprocfamily" OID NOT NULL,
		"amproclefttype" OID NOT NULL,
		"amprocrighttype" OID NOT NULL,
		"amprocnum" INT2 NOT NULL,
		"amproc" REGPROC NOT NULL
	);

CREATE TABLE "public"."t_permission" (
		"id" BIGSERIAL DEFAULT nextval('permission_id_seq'::regclass) NOT NULL,
		"description" VARCHAR(255),
		"entity_ids" VARCHAR(255),
		"name" VARCHAR(255),
		"permission" VARCHAR(255)
	);

CREATE TABLE "pg_catalog"."pg_rewrite" (
		"rulename" CIDR(64) NOT NULL,
		"ev_class" OID NOT NULL,
		"ev_type" null NOT NULL,
		"ev_enabled" null NOT NULL,
		"is_instead" BOOL NOT NULL,
		"ev_qual" null,
		"ev_action" null
	);

CREATE TABLE "pg_catalog"."pg_replication_origin" (
		"roident" OID NOT NULL,
		"roname" TEXT NOT NULL
	);

CREATE TABLE "pg_catalog"."pg_aggregate" (
		"aggfnoid" REGPROC NOT NULL,
		"aggkind" null NOT NULL,
		"aggnumdirectargs" INT2 NOT NULL,
		"aggtransfn" REGPROC NOT NULL,
		"aggfinalfn" REGPROC NOT NULL,
		"aggmtransfn" REGPROC NOT NULL,
		"aggminvtransfn" REGPROC NOT NULL,
		"aggmfinalfn" REGPROC NOT NULL,
		"aggfinalextra" BOOL NOT NULL,
		"aggmfinalextra" BOOL NOT NULL,
		"aggsortop" OID NOT NULL,
		"aggtranstype" OID NOT NULL,
		"aggtransspace" INT4 NOT NULL,
		"aggmtranstype" OID NOT NULL,
		"aggmtransspace" INT4 NOT NULL,
		"agginitval" TEXT,
		"aggminitval" TEXT
	);

CREATE TABLE "pg_catalog"."pg_auth_members" (
		"roleid" OID NOT NULL,
		"member" OID NOT NULL,
		"grantor" OID NOT NULL,
		"admin_option" BOOL NOT NULL
	);

CREATE TABLE "public"."t_menu" (
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

CREATE TABLE "pg_catalog"."pg_enum" (
		"enumtypid" OID NOT NULL,
		"enumsortorder" FLOAT4 NOT NULL,
		"enumlabel" CIDR(64) NOT NULL
	);

CREATE TABLE "pg_catalog"."pg_conversion" (
		"conname" CIDR(64) NOT NULL,
		"connamespace" OID NOT NULL,
		"conowner" OID NOT NULL,
		"conforencoding" INT4 NOT NULL,
		"contoencoding" INT4 NOT NULL,
		"conproc" REGPROC NOT NULL,
		"condefault" BOOL NOT NULL
	);

CREATE TABLE "pg_catalog"."pg_shdepend" (
		"dbid" OID NOT NULL,
		"classid" OID NOT NULL,
		"objid" OID NOT NULL,
		"objsubid" INT4 NOT NULL,
		"refclassid" OID NOT NULL,
		"refobjid" OID NOT NULL,
		"deptype" null NOT NULL
	);

CREATE TABLE "pg_catalog"."pg_foreign_data_wrapper" (
		"fdwname" CIDR(64) NOT NULL,
		"fdwowner" OID NOT NULL,
		"fdwhandler" OID NOT NULL,
		"fdwvalidator" OID NOT NULL,
		"fdwacl" ACLITEM[ ],
		"fdwoptions" TEXT[ ]
	);

CREATE TABLE "pg_catalog"."pg_attribute" (
		"attrelid" OID NOT NULL,
		"attname" CIDR(64) NOT NULL,
		"atttypid" OID NOT NULL,
		"attstattarget" INT4 NOT NULL,
		"attlen" INT2 NOT NULL,
		"attnum" INT2 NOT NULL,
		"attndims" INT4 NOT NULL,
		"attcacheoff" INT4 NOT NULL,
		"atttypmod" INT4 NOT NULL,
		"attbyval" BOOL NOT NULL,
		"attstorage" null NOT NULL,
		"attalign" null NOT NULL,
		"attnotnull" BOOL NOT NULL,
		"atthasdef" BOOL NOT NULL,
		"attisdropped" BOOL NOT NULL,
		"attislocal" BOOL NOT NULL,
		"attinhcount" INT4 NOT NULL,
		"attcollation" OID NOT NULL,
		"attacl" ACLITEM[ ],
		"attoptions" TEXT[ ],
		"attfdwoptions" TEXT[ ]
	);

CREATE TABLE "pg_catalog"."pg_proc" (
		"proname" CIDR(64) NOT NULL,
		"pronamespace" OID NOT NULL,
		"proowner" OID NOT NULL,
		"prolang" OID NOT NULL,
		"procost" FLOAT4 NOT NULL,
		"prorows" FLOAT4 NOT NULL,
		"provariadic" OID NOT NULL,
		"protransform" REGPROC NOT NULL,
		"proisagg" BOOL NOT NULL,
		"proiswindow" BOOL NOT NULL,
		"prosecdef" BOOL NOT NULL,
		"proleakproof" BOOL NOT NULL,
		"proisstrict" BOOL NOT NULL,
		"proretset" BOOL NOT NULL,
		"provolatile" null NOT NULL,
		"pronargs" INT2 NOT NULL,
		"pronargdefaults" INT2 NOT NULL,
		"prorettype" OID NOT NULL,
		"proargtypes" null NOT NULL,
		"proallargtypes" OID[ ],
		"proargmodes" null,
		"proargnames" TEXT[ ],
		"proargdefaults" null,
		"protrftypes" OID[ ],
		"prosrc" TEXT NOT NULL,
		"probin" TEXT,
		"proconfig" TEXT[ ],
		"proacl" ACLITEM[ ]
	);

CREATE TABLE "pg_catalog"."pg_transform" (
		"trftype" OID NOT NULL,
		"trflang" OID NOT NULL,
		"trffromsql" REGPROC NOT NULL,
		"trftosql" REGPROC NOT NULL
	);

CREATE TABLE "pg_catalog"."pg_opclass" (
		"opcmethod" OID NOT NULL,
		"opcname" CIDR(64) NOT NULL,
		"opcnamespace" OID NOT NULL,
		"opcowner" OID NOT NULL,
		"opcfamily" OID NOT NULL,
		"opcintype" OID NOT NULL,
		"opcdefault" BOOL NOT NULL,
		"opckeytype" OID NOT NULL
	);

CREATE TABLE "pg_catalog"."pg_seclabel" (
		"objoid" OID NOT NULL,
		"classoid" OID NOT NULL,
		"objsubid" INT4 NOT NULL,
		"provider" TEXT NOT NULL,
		"label" TEXT NOT NULL
	);

CREATE TABLE "pg_catalog"."pg_type" (
		"typname" CIDR(64) NOT NULL,
		"typnamespace" OID NOT NULL,
		"typowner" OID NOT NULL,
		"typlen" INT2 NOT NULL,
		"typbyval" BOOL NOT NULL,
		"typtype" null NOT NULL,
		"typcategory" null NOT NULL,
		"typispreferred" BOOL NOT NULL,
		"typisdefined" BOOL NOT NULL,
		"typdelim" null NOT NULL,
		"typrelid" OID NOT NULL,
		"typelem" OID NOT NULL,
		"typarray" OID NOT NULL,
		"typinput" REGPROC NOT NULL,
		"typoutput" REGPROC NOT NULL,
		"typreceive" REGPROC NOT NULL,
		"typsend" REGPROC NOT NULL,
		"typmodin" REGPROC NOT NULL,
		"typmodout" REGPROC NOT NULL,
		"typanalyze" REGPROC NOT NULL,
		"typalign" null NOT NULL,
		"typstorage" null NOT NULL,
		"typnotnull" BOOL NOT NULL,
		"typbasetype" OID NOT NULL,
		"typtypmod" INT4 NOT NULL,
		"typndims" INT4 NOT NULL,
		"typcollation" OID NOT NULL,
		"typdefaultbin" null,
		"typdefault" TEXT,
		"typacl" ACLITEM[ ]
	);

CREATE TABLE "public"."t_role_permission" (
		"role_id" INT8 NOT NULL,
		"permission_id" INT8 NOT NULL
	);

CREATE TABLE "pg_catalog"."pg_description" (
		"objoid" OID NOT NULL,
		"classoid" OID NOT NULL,
		"objsubid" INT4 NOT NULL,
		"description" TEXT NOT NULL
	);

CREATE TABLE "pg_catalog"."pg_default_acl" (
		"defaclrole" OID NOT NULL,
		"defaclnamespace" OID NOT NULL,
		"defaclobjtype" null NOT NULL,
		"defaclacl" ACLITEM[ ]
	);

CREATE TABLE "public"."t_role" (
		"id" BIGSERIAL DEFAULT nextval('role_id_seq'::regclass) NOT NULL,
		"description" VARCHAR(360),
		"role_name" VARCHAR(26)
	);

CREATE TABLE "pg_catalog"."pg_amop" (
		"amopfamily" OID NOT NULL,
		"amoplefttype" OID NOT NULL,
		"amoprighttype" OID NOT NULL,
		"amopstrategy" INT2 NOT NULL,
		"amoppurpose" null NOT NULL,
		"amopopr" OID NOT NULL,
		"amopmethod" OID NOT NULL,
		"amopsortfamily" OID NOT NULL
	);

CREATE TABLE "pg_catalog"."pg_authid" (
		"rolname" CIDR(64) NOT NULL,
		"rolsuper" BOOL NOT NULL,
		"rolinherit" BOOL NOT NULL,
		"rolcreaterole" BOOL NOT NULL,
		"rolcreatedb" BOOL NOT NULL,
		"rolcanlogin" BOOL NOT NULL,
		"rolreplication" BOOL NOT NULL,
		"rolbypassrls" BOOL NOT NULL,
		"rolconnlimit" INT4 NOT NULL,
		"rolpassword" TEXT,
		"rolvaliduntil" TIMESTAMPTZ
	);

CREATE TABLE "pg_catalog"."pg_namespace" (
		"nspname" CIDR(64) NOT NULL,
		"nspowner" OID NOT NULL,
		"nspacl" ACLITEM[ ]
	);

CREATE TABLE "pg_catalog"."pg_largeobject" (
		"loid" OID NOT NULL,
		"pageno" INT4 NOT NULL,
		"data" BYTEA NOT NULL
	);

CREATE TABLE "pg_catalog"."pg_shdescription" (
		"objoid" OID NOT NULL,
		"classoid" OID NOT NULL,
		"description" TEXT NOT NULL
	);

CREATE TABLE "pg_catalog"."pg_ts_template" (
		"tmplname" CIDR(64) NOT NULL,
		"tmplnamespace" OID NOT NULL,
		"tmplinit" REGPROC NOT NULL,
		"tmpllexize" REGPROC NOT NULL
	);

CREATE TABLE "pg_catalog"."pg_pltemplate" (
		"tmplname" CIDR(64) NOT NULL,
		"tmpltrusted" BOOL NOT NULL,
		"tmpldbacreate" BOOL NOT NULL,
		"tmplhandler" TEXT NOT NULL,
		"tmplinline" TEXT,
		"tmplvalidator" TEXT,
		"tmpllibrary" TEXT NOT NULL,
		"tmplacl" ACLITEM[ ]
	);

CREATE TABLE "pg_catalog"."pg_shseclabel" (
		"objoid" OID NOT NULL,
		"classoid" OID NOT NULL,
		"provider" TEXT NOT NULL,
		"label" TEXT NOT NULL
	);

CREATE TABLE "pg_catalog"."pg_am" (
		"amname" CIDR(64) NOT NULL,
		"amstrategies" INT2 NOT NULL,
		"amsupport" INT2 NOT NULL,
		"amcanorder" BOOL NOT NULL,
		"amcanorderbyop" BOOL NOT NULL,
		"amcanbackward" BOOL NOT NULL,
		"amcanunique" BOOL NOT NULL,
		"amcanmulticol" BOOL NOT NULL,
		"amoptionalkey" BOOL NOT NULL,
		"amsearcharray" BOOL NOT NULL,
		"amsearchnulls" BOOL NOT NULL,
		"amstorage" BOOL NOT NULL,
		"amclusterable" BOOL NOT NULL,
		"ampredlocks" BOOL NOT NULL,
		"amkeytype" OID NOT NULL,
		"aminsert" REGPROC NOT NULL,
		"ambeginscan" REGPROC NOT NULL,
		"amgettuple" REGPROC NOT NULL,
		"amgetbitmap" REGPROC NOT NULL,
		"amrescan" REGPROC NOT NULL,
		"amendscan" REGPROC NOT NULL,
		"ammarkpos" REGPROC NOT NULL,
		"amrestrpos" REGPROC NOT NULL,
		"ambuild" REGPROC NOT NULL,
		"ambuildempty" REGPROC NOT NULL,
		"ambulkdelete" REGPROC NOT NULL,
		"amvacuumcleanup" REGPROC NOT NULL,
		"amcanreturn" REGPROC NOT NULL,
		"amcostestimate" REGPROC NOT NULL,
		"amoptions" REGPROC NOT NULL
	);

CREATE TABLE "pg_catalog"."pg_largeobject_metadata" (
		"lomowner" OID NOT NULL,
		"lomacl" ACLITEM[ ]
	);

CREATE TABLE "pg_catalog"."pg_opfamily" (
		"opfmethod" OID NOT NULL,
		"opfname" CIDR(64) NOT NULL,
		"opfnamespace" OID NOT NULL,
		"opfowner" OID NOT NULL
	);

CREATE TABLE "pg_catalog"."pg_ts_config_map" (
		"mapcfg" OID NOT NULL,
		"maptokentype" INT4 NOT NULL,
		"mapseqno" INT4 NOT NULL,
		"mapdict" OID NOT NULL
	);

CREATE TABLE "public"."t_user" (
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

CREATE TABLE "pg_catalog"."pg_extension" (
		"extname" CIDR(64) NOT NULL,
		"extowner" OID NOT NULL,
		"extnamespace" OID NOT NULL,
		"extrelocatable" BOOL NOT NULL,
		"extversion" TEXT NOT NULL,
		"extconfig" OID[ ],
		"extcondition" TEXT[ ]
	);

CREATE TABLE "pg_catalog"."pg_collation" (
		"collname" CIDR(64) NOT NULL,
		"collnamespace" OID NOT NULL,
		"collowner" OID NOT NULL,
		"collencoding" INT4 NOT NULL,
		"collcollate" CIDR(64) NOT NULL,
		"collctype" CIDR(64) NOT NULL
	);

CREATE TABLE "pg_catalog"."pg_foreign_server" (
		"srvname" CIDR(64) NOT NULL,
		"srvowner" OID NOT NULL,
		"srvfdw" OID NOT NULL,
		"srvtype" TEXT,
		"srvversion" TEXT,
		"srvacl" ACLITEM[ ],
		"srvoptions" TEXT[ ]
	);

CREATE TABLE "public"."t_system_info" (
		"id" BIGSERIAL DEFAULT nextval('system_info_id_seq'::regclass) NOT NULL,
		"description" VARCHAR(480),
		"keyname" VARCHAR(90) NOT NULL,
		"organize" VARCHAR(180),
		"type" VARCHAR(255),
		"value" VARCHAR(3000)
	);

CREATE TABLE "pg_catalog"."pg_language" (
		"lanname" CIDR(64) NOT NULL,
		"lanowner" OID NOT NULL,
		"lanispl" BOOL NOT NULL,
		"lanpltrusted" BOOL NOT NULL,
		"lanplcallfoid" OID NOT NULL,
		"laninline" OID NOT NULL,
		"lanvalidator" OID NOT NULL,
		"lanacl" ACLITEM[ ]
	);

CREATE TABLE "pg_catalog"."pg_depend" (
		"classid" OID NOT NULL,
		"objid" OID NOT NULL,
		"objsubid" INT4 NOT NULL,
		"refclassid" OID NOT NULL,
		"refobjid" OID NOT NULL,
		"refobjsubid" INT4 NOT NULL,
		"deptype" null NOT NULL
	);

CREATE TABLE "pg_catalog"."pg_ts_config" (
		"cfgname" CIDR(64) NOT NULL,
		"cfgnamespace" OID NOT NULL,
		"cfgowner" OID NOT NULL,
		"cfgparser" OID NOT NULL
	);

CREATE TABLE "pg_catalog"."pg_ts_parser" (
		"prsname" CIDR(64) NOT NULL,
		"prsnamespace" OID NOT NULL,
		"prsstart" REGPROC NOT NULL,
		"prstoken" REGPROC NOT NULL,
		"prsend" REGPROC NOT NULL,
		"prsheadline" REGPROC NOT NULL,
		"prslextype" REGPROC NOT NULL
	);

CREATE TABLE "pg_catalog"."pg_class" (
		"relname" CIDR(64) NOT NULL,
		"relnamespace" OID NOT NULL,
		"reltype" OID NOT NULL,
		"reloftype" OID NOT NULL,
		"relowner" OID NOT NULL,
		"relam" OID NOT NULL,
		"relfilenode" OID NOT NULL,
		"reltablespace" OID NOT NULL,
		"relpages" INT4 NOT NULL,
		"reltuples" FLOAT4 NOT NULL,
		"relallvisible" INT4 NOT NULL,
		"reltoastrelid" OID NOT NULL,
		"relhasindex" BOOL NOT NULL,
		"relisshared" BOOL NOT NULL,
		"relpersistence" null NOT NULL,
		"relkind" null NOT NULL,
		"relnatts" INT2 NOT NULL,
		"relchecks" INT2 NOT NULL,
		"relhasoids" BOOL NOT NULL,
		"relhaspkey" BOOL NOT NULL,
		"relhasrules" BOOL NOT NULL,
		"relhastriggers" BOOL NOT NULL,
		"relhassubclass" BOOL NOT NULL,
		"relrowsecurity" BOOL NOT NULL,
		"relforcerowsecurity" BOOL NOT NULL,
		"relispopulated" BOOL NOT NULL,
		"relreplident" null NOT NULL,
		"relfrozenxid" XID NOT NULL,
		"relminmxid" XID NOT NULL,
		"relacl" ACLITEM[ ],
		"reloptions" TEXT[ ]
	);

CREATE TABLE "pg_catalog"."pg_cast" (
		"castsource" OID NOT NULL,
		"casttarget" OID NOT NULL,
		"castfunc" OID NOT NULL,
		"castcontext" null NOT NULL,
		"castmethod" null NOT NULL
	);

CREATE TABLE "pg_catalog"."pg_tablespace" (
		"spcname" CIDR(64) NOT NULL,
		"spcowner" OID NOT NULL,
		"spcacl" ACLITEM[ ],
		"spcoptions" TEXT[ ]
	);

CREATE TABLE "pg_catalog"."pg_statistic" (
		"starelid" OID NOT NULL,
		"staattnum" INT2 NOT NULL,
		"stainherit" BOOL NOT NULL,
		"stanullfrac" FLOAT4 NOT NULL,
		"stawidth" INT4 NOT NULL,
		"stadistinct" FLOAT4 NOT NULL,
		"stakind1" INT2 NOT NULL,
		"stakind2" INT2 NOT NULL,
		"stakind3" INT2 NOT NULL,
		"stakind4" INT2 NOT NULL,
		"stakind5" INT2 NOT NULL,
		"staop1" OID NOT NULL,
		"staop2" OID NOT NULL,
		"staop3" OID NOT NULL,
		"staop4" OID NOT NULL,
		"staop5" OID NOT NULL,
		"stanumbers1" FLOAT4[ ],
		"stanumbers2" FLOAT4[ ],
		"stanumbers3" FLOAT4[ ],
		"stanumbers4" FLOAT4[ ],
		"stanumbers5" FLOAT4[ ],
		"stavalues1" null,
		"stavalues2" null,
		"stavalues3" null,
		"stavalues4" null,
		"stavalues5" null
	);

CREATE TABLE "pg_catalog"."pg_event_trigger" (
		"evtname" CIDR(64) NOT NULL,
		"evtevent" CIDR(64) NOT NULL,
		"evtowner" OID NOT NULL,
		"evtfoid" OID NOT NULL,
		"evtenabled" null NOT NULL,
		"evttags" TEXT[ ]
	);

CREATE TABLE "public"."t_navigation" (
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

CREATE TABLE "pg_catalog"."pg_inherits" (
		"inhrelid" OID NOT NULL,
		"inhparent" OID NOT NULL,
		"inhseqno" INT4 NOT NULL
	);

CREATE TABLE "pg_catalog"."pg_trigger" (
		"tgrelid" OID NOT NULL,
		"tgname" CIDR(64) NOT NULL,
		"tgfoid" OID NOT NULL,
		"tgtype" INT2 NOT NULL,
		"tgenabled" null NOT NULL,
		"tgisinternal" BOOL NOT NULL,
		"tgconstrrelid" OID NOT NULL,
		"tgconstrindid" OID NOT NULL,
		"tgconstraint" OID NOT NULL,
		"tgdeferrable" BOOL NOT NULL,
		"tginitdeferred" BOOL NOT NULL,
		"tgnargs" INT2 NOT NULL,
		"tgattr" null NOT NULL,
		"tgargs" BYTEA NOT NULL,
		"tgqual" null
	);

CREATE TABLE "public"."t_article" (
		"id" BIGSERIAL DEFAULT nextval('article_id_seq'::regclass) NOT NULL,
		"context" TEXT,
		"menu_id" INT8 NOT NULL,
		"uploadtime" TIMESTAMP DEFAULT now()
	);

CREATE TABLE "public"."t_gantt" (
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

CREATE TABLE "pg_catalog"."pg_attrdef" (
		"adrelid" OID NOT NULL,
		"adnum" INT2 NOT NULL,
		"adbin" null,
		"adsrc" TEXT
	);

CREATE TABLE "public"."t_user_permission" (
		"user_id" INT8 NOT NULL,
		"permission_id" INT8 NOT NULL
	);

CREATE TABLE "public"."t_url_filter" (
		"id" BIGSERIAL DEFAULT nextval('url_filter_id_seq'::regclass) NOT NULL,
		"name" VARCHAR(86),
		"permissions" VARCHAR(128),
		"roles" VARCHAR(480),
		"sort" INT8,
		"url" VARCHAR(480)
	);

CREATE TABLE "public"."t_shortcut" (
		"id" BIGSERIAL DEFAULT nextval('shortcut_id_seq'::regclass) NOT NULL,
		"app_class" VARCHAR(254) NOT NULL,
		"icon_cls" VARCHAR(128) NOT NULL,
		"module" VARCHAR(64) NOT NULL,
		"name" VARCHAR(64) NOT NULL
	);

CREATE TABLE "pg_catalog"."pg_operator" (
		"oprname" CIDR(64) NOT NULL,
		"oprnamespace" OID NOT NULL,
		"oprowner" OID NOT NULL,
		"oprkind" null NOT NULL,
		"oprcanmerge" BOOL NOT NULL,
		"oprcanhash" BOOL NOT NULL,
		"oprleft" OID NOT NULL,
		"oprright" OID NOT NULL,
		"oprresult" OID NOT NULL,
		"oprcom" OID NOT NULL,
		"oprnegate" OID NOT NULL,
		"oprcode" REGPROC NOT NULL,
		"oprrest" REGPROC NOT NULL,
		"oprjoin" REGPROC NOT NULL
	);

CREATE TABLE "pg_catalog"."pg_db_role_setting" (
		"setdatabase" OID NOT NULL,
		"setrole" OID NOT NULL,
		"setconfig" TEXT[ ]
	);

CREATE TABLE "pg_catalog"."pg_ts_dict" (
		"dictname" CIDR(64) NOT NULL,
		"dictnamespace" OID NOT NULL,
		"dictowner" OID NOT NULL,
		"dicttemplate" OID NOT NULL,
		"dictinitoption" TEXT
	);

CREATE TABLE "pg_catalog"."pg_user_mapping" (
		"umuser" OID NOT NULL,
		"umserver" OID NOT NULL,
		"umoptions" TEXT[ ]
	);

CREATE TABLE "public"."t_user_role" (
		"user_id" INT8 NOT NULL,
		"role_id" INT8 NOT NULL
	);

CREATE TABLE "pg_catalog"."pg_policy" (
		"polname" CIDR(64) NOT NULL,
		"polrelid" OID NOT NULL,
		"polcmd" null NOT NULL,
		"polroles" OID[ ],
		"polqual" null,
		"polwithcheck" null
	);

CREATE TABLE "pg_catalog"."pg_database" (
		"datname" CIDR(64) NOT NULL,
		"datdba" OID NOT NULL,
		"encoding" INT4 NOT NULL,
		"datcollate" CIDR(64) NOT NULL,
		"datctype" CIDR(64) NOT NULL,
		"datistemplate" BOOL NOT NULL,
		"datallowconn" BOOL NOT NULL,
		"datconnlimit" INT4 NOT NULL,
		"datlastsysoid" OID NOT NULL,
		"datfrozenxid" XID NOT NULL,
		"datminmxid" XID NOT NULL,
		"dattablespace" OID NOT NULL,
		"datacl" ACLITEM[ ]
	);

CREATE UNIQUE INDEX "public"."t_menu_pkey" ON "public"."t_menu" ("id" ASC);

CREATE UNIQUE INDEX "pg_catalog"."pg_opfamily_oid_index" ON "pg_catalog"."pg_opfamily" (null);

CREATE UNIQUE INDEX "pg_catalog"."pg_amop_fam_strat_index" ON "pg_catalog"."pg_amop" ("amopfamily" ASC, "amoplefttype" ASC, "amoprighttype" ASC, "amopstrategy" ASC);

CREATE UNIQUE INDEX "public"."ukowrvid0ae51dah41gruorxdqi" ON "public"."t_role" ("id" ASC, "role_name" ASC);

CREATE UNIQUE INDEX "pg_catalog"."pg_attribute_relid_attnam_index" ON "pg_catalog"."pg_attribute" ("attrelid" ASC, "attname" ASC);

CREATE UNIQUE INDEX "public"."ukln4nygxodh1vmpk5nep24umdx" ON "public"."t_role_permission" ("role_id" ASC, "permission_id" ASC);

CREATE UNIQUE INDEX "pg_catalog"."pg_operator_oprname_l_r_n_index" ON "pg_catalog"."pg_operator" ("oprname" ASC, "oprleft" ASC, "oprright" ASC, "oprnamespace" ASC);

CREATE UNIQUE INDEX "pg_catalog"."pg_index_indexrelid_index" ON "pg_catalog"."pg_index" ("indexrelid" ASC);

CREATE INDEX "pg_catalog"."pg_constraint_contypid_index" ON "pg_catalog"."pg_constraint" ("contypid" ASC);

CREATE UNIQUE INDEX "pg_catalog"."pg_rewrite_rel_rulename_index" ON "pg_catalog"."pg_rewrite" ("ev_class" ASC, "rulename" ASC);

CREATE INDEX "pg_catalog"."pg_class_tblspc_relfilenode_index" ON "pg_catalog"."pg_class" ("reltablespace" ASC, "relfilenode" ASC);

CREATE UNIQUE INDEX "public"."uk1jk3ouih7o51iamm4tjtig62k" ON "public"."t_url_filter" ("id" ASC, "url" ASC);

CREATE INDEX "pg_catalog"."pg_depend_depender_index" ON "pg_catalog"."pg_depend" ("classid" ASC, "objid" ASC, "objsubid" ASC);

CREATE UNIQUE INDEX "public"."uk2j13xyyh7chh9gdg1dgvitd32" ON "public"."t_navigation" ("alias" ASC);

CREATE UNIQUE INDEX "pg_catalog"."pg_trigger_tgrelid_tgname_index" ON "pg_catalog"."pg_trigger" ("tgrelid" ASC, "tgname" ASC);

CREATE UNIQUE INDEX "pg_catalog"."pg_language_oid_index" ON "pg_catalog"."pg_language" (null);

CREATE UNIQUE INDEX "pg_catalog"."pg_amop_opr_fam_index" ON "pg_catalog"."pg_amop" ("amopopr" ASC, "amoppurpose" ASC, "amopfamily" ASC);

CREATE UNIQUE INDEX "pg_catalog"."pg_type_oid_index" ON "pg_catalog"."pg_type" (null);

CREATE UNIQUE INDEX "pg_catalog"."pg_ts_parser_prsname_index" ON "pg_catalog"."pg_ts_parser" ("prsname" ASC, "prsnamespace" ASC);

CREATE UNIQUE INDEX "pg_catalog"."pg_user_mapping_oid_index" ON "pg_catalog"."pg_user_mapping" (null);

CREATE UNIQUE INDEX "pg_catalog"."pg_amproc_fam_proc_index" ON "pg_catalog"."pg_amproc" ("amprocfamily" ASC, "amproclefttype" ASC, "amprocrighttype" ASC, "amprocnum" ASC);

CREATE UNIQUE INDEX "pg_catalog"."pg_collation_oid_index" ON "pg_catalog"."pg_collation" (null);

CREATE UNIQUE INDEX "pg_catalog"."pg_ts_template_oid_index" ON "pg_catalog"."pg_ts_template" (null);

CREATE UNIQUE INDEX "pg_catalog"."pg_attrdef_oid_index" ON "pg_catalog"."pg_attrdef" (null);

CREATE UNIQUE INDEX "pg_catalog"."pg_ts_template_tmplname_index" ON "pg_catalog"."pg_ts_template" ("tmplname" ASC, "tmplnamespace" ASC);

CREATE UNIQUE INDEX "public"."uknk3llagr5tjnvhmvpomcxohj1" ON "public"."t_menu" ("alias" ASC);

CREATE INDEX "pg_catalog"."pg_shdepend_depender_index" ON "pg_catalog"."pg_shdepend" ("dbid" ASC, "classid" ASC, "objid" ASC, "objsubid" ASC);

CREATE UNIQUE INDEX "public"."t_navigation_pkey" ON "public"."t_navigation" ("id" ASC);

CREATE UNIQUE INDEX "pg_catalog"."pg_amproc_oid_index" ON "pg_catalog"."pg_amproc" (null);

CREATE UNIQUE INDEX "pg_catalog"."pg_enum_oid_index" ON "pg_catalog"."pg_enum" (null);

CREATE INDEX "pg_catalog"."pg_constraint_conname_nsp_index" ON "pg_catalog"."pg_constraint" ("conname" ASC, "connamespace" ASC);

CREATE UNIQUE INDEX "pg_catalog"."pg_aggregate_fnoid_index" ON "pg_catalog"."pg_aggregate" ("aggfnoid" ASC);

CREATE UNIQUE INDEX "public"."t_article_pkey" ON "public"."t_article" ("id" ASC);

CREATE UNIQUE INDEX "public"."t_user_pkey" ON "public"."t_user" ("id" ASC);

CREATE UNIQUE INDEX "public"."ukpju8cmo3uddy73n2a8ath3vp3" ON "public"."t_article" ("menu_id" ASC);

CREATE UNIQUE INDEX "public"."t_shortcut_pkey" ON "public"."t_shortcut" ("id" ASC);

CREATE UNIQUE INDEX "public"."uk_jhib4legehrm4yscx9t3lirqi" ON "public"."t_user" ("username" ASC);

CREATE UNIQUE INDEX "pg_catalog"."pg_namespace_oid_index" ON "pg_catalog"."pg_namespace" (null);

CREATE UNIQUE INDEX "pg_catalog"."pg_foreign_table_relid_index" ON "pg_catalog"."pg_foreign_table" ("ftrelid" ASC);

CREATE UNIQUE INDEX "public"."uk_rnsj7fkgeoob0wy0nlb32j44h" ON "public"."t_system_info" ("keyname" ASC);

CREATE UNIQUE INDEX "pg_catalog"."pg_replication_origin_roname_index" ON "pg_catalog"."pg_replication_origin" ("roname" ASC);

CREATE UNIQUE INDEX "pg_catalog"."pg_trigger_oid_index" ON "pg_catalog"."pg_trigger" (null);

CREATE UNIQUE INDEX "pg_catalog"."pg_opfamily_am_name_nsp_index" ON "pg_catalog"."pg_opfamily" ("opfmethod" ASC, "opfname" ASC, "opfnamespace" ASC);

CREATE UNIQUE INDEX "pg_catalog"."pg_ts_parser_oid_index" ON "pg_catalog"."pg_ts_parser" (null);

CREATE UNIQUE INDEX "pg_catalog"."pg_shdescription_o_c_index" ON "pg_catalog"."pg_shdescription" ("objoid" ASC, "classoid" ASC);

CREATE UNIQUE INDEX "public"."t_url_filter_pkey" ON "public"."t_url_filter" ("id" ASC);

CREATE UNIQUE INDEX "public"."uk8cr5punfsvecweus4ftuppadx" ON "public"."t_system_info" ("id" ASC, "keyname" ASC);

CREATE UNIQUE INDEX "pg_catalog"."pg_class_oid_index" ON "pg_catalog"."pg_class" (null);

CREATE UNIQUE INDEX "pg_catalog"."pg_opclass_am_name_nsp_index" ON "pg_catalog"."pg_opclass" ("opcmethod" ASC, "opcname" ASC, "opcnamespace" ASC);

CREATE UNIQUE INDEX "pg_catalog"."pg_shseclabel_object_index" ON "pg_catalog"."pg_shseclabel" ("objoid" ASC, "classoid" ASC, "provider" ASC);

CREATE INDEX "public"."idx2j13xyyh7chh9gdg1dgvitd32" ON "public"."t_navigation" ("alias" ASC);

CREATE UNIQUE INDEX "pg_catalog"."pg_enum_typid_sortorder_index" ON "pg_catalog"."pg_enum" ("enumtypid" ASC, "enumsortorder" ASC);

CREATE INDEX "pg_catalog"."pg_depend_reference_index" ON "pg_catalog"."pg_depend" ("refclassid" ASC, "refobjid" ASC, "refobjsubid" ASC);

CREATE UNIQUE INDEX "pg_catalog"."pg_replication_origin_roiident_index" ON "pg_catalog"."pg_replication_origin" ("roident" ASC);

CREATE UNIQUE INDEX "pg_catalog"."pg_pltemplate_name_index" ON "pg_catalog"."pg_pltemplate" ("tmplname" ASC);

CREATE UNIQUE INDEX "pg_catalog"."pg_cast_oid_index" ON "pg_catalog"."pg_cast" (null);

CREATE UNIQUE INDEX "public"."t_permission_pkey" ON "public"."t_permission" ("id" ASC);

CREATE UNIQUE INDEX "public"."uk_bojr4duyesjymks0ty5yjwhx3" ON "public"."t_role" ("role_name" ASC);

CREATE UNIQUE INDEX "pg_catalog"."pg_type_typname_nsp_index" ON "pg_catalog"."pg_type" ("typname" ASC, "typnamespace" ASC);

CREATE INDEX "pg_catalog"."pg_constraint_conrelid_index" ON "pg_catalog"."pg_constraint" ("conrelid" ASC);

CREATE UNIQUE INDEX "pg_catalog"."pg_language_name_index" ON "pg_catalog"."pg_language" ("lanname" ASC);

CREATE UNIQUE INDEX "pg_catalog"."pg_proc_proname_args_nsp_index" ON "pg_catalog"."pg_proc" ("proname" ASC, "proargtypes" ASC, "pronamespace" ASC);

CREATE UNIQUE INDEX "pg_catalog"."pg_inherits_relid_seqno_index" ON "pg_catalog"."pg_inherits" ("inhrelid" ASC, "inhseqno" ASC);

CREATE UNIQUE INDEX "pg_catalog"."pg_event_trigger_oid_index" ON "pg_catalog"."pg_event_trigger" (null);

CREATE UNIQUE INDEX "pg_catalog"."pg_description_o_c_o_index" ON "pg_catalog"."pg_description" ("objoid" ASC, "classoid" ASC, "objsubid" ASC);

CREATE UNIQUE INDEX "public"."ukrqhb827itbpcilv0qcfbvm2ep" ON "public"."t_user" ("id" ASC, "username" ASC, "email" ASC, "phone" ASC);

CREATE UNIQUE INDEX "public"."t_gantt_pkey" ON "public"."t_gantt" ("id" ASC);

CREATE INDEX "pg_catalog"."pg_shdepend_reference_index" ON "pg_catalog"."pg_shdepend" ("refclassid" ASC, "refobjid" ASC);

CREATE UNIQUE INDEX "pg_catalog"."pg_ts_config_cfgname_index" ON "pg_catalog"."pg_ts_config" ("cfgname" ASC, "cfgnamespace" ASC);

CREATE UNIQUE INDEX "pg_catalog"."pg_ts_config_map_index" ON "pg_catalog"."pg_ts_config_map" ("mapcfg" ASC, "maptokentype" ASC, "mapseqno" ASC);

CREATE UNIQUE INDEX "pg_catalog"."pg_namespace_nspname_index" ON "pg_catalog"."pg_namespace" ("nspname" ASC);

CREATE UNIQUE INDEX "public"."uk_i6qjjoe560mee5ajdg7v1o6mi" ON "public"."t_user" ("email" ASC);

CREATE UNIQUE INDEX "pg_catalog"."pg_conversion_default_index" ON "pg_catalog"."pg_conversion" ("connamespace" ASC, "conforencoding" ASC, "contoencoding" ASC);

CREATE UNIQUE INDEX "pg_catalog"."pg_foreign_data_wrapper_name_index" ON "pg_catalog"."pg_foreign_data_wrapper" ("fdwname" ASC);

CREATE UNIQUE INDEX "pg_catalog"."pg_seclabel_object_index" ON "pg_catalog"."pg_seclabel" ("objoid" ASC, "classoid" ASC, "objsubid" ASC, "provider" ASC);

CREATE UNIQUE INDEX "public"."uk_gxvxv0ec1k0ogosdolmtu3l2y" ON "public"."t_shortcut" ("module" ASC);

CREATE UNIQUE INDEX "pg_catalog"."pg_attrdef_adrelid_adnum_index" ON "pg_catalog"."pg_attrdef" ("adrelid" ASC, "adnum" ASC);

CREATE INDEX "pg_catalog"."pg_index_indrelid_index" ON "pg_catalog"."pg_index" ("indrelid" ASC);

CREATE UNIQUE INDEX "pg_catalog"."pg_class_relname_nsp_index" ON "pg_catalog"."pg_class" ("relname" ASC, "relnamespace" ASC);

CREATE UNIQUE INDEX "pg_catalog"."pg_largeobject_metadata_oid_index" ON "pg_catalog"."pg_largeobject_metadata" (null);

CREATE UNIQUE INDEX "public"."t_system_info_pkey" ON "public"."t_system_info" ("id" ASC);

CREATE UNIQUE INDEX "pg_catalog"."pg_database_oid_index" ON "pg_catalog"."pg_database" (null);

CREATE UNIQUE INDEX "pg_catalog"."pg_event_trigger_evtname_index" ON "pg_catalog"."pg_event_trigger" ("evtname" ASC);

CREATE INDEX "pg_catalog"."pg_inherits_parent_index" ON "pg_catalog"."pg_inherits" ("inhparent" ASC);

CREATE UNIQUE INDEX "pg_catalog"."pg_ts_dict_oid_index" ON "pg_catalog"."pg_ts_dict" (null);

CREATE UNIQUE INDEX "pg_catalog"."pg_default_acl_oid_index" ON "pg_catalog"."pg_default_acl" (null);

CREATE UNIQUE INDEX "pg_catalog"."pg_conversion_oid_index" ON "pg_catalog"."pg_conversion" (null);

CREATE UNIQUE INDEX "pg_catalog"."pg_foreign_server_oid_index" ON "pg_catalog"."pg_foreign_server" (null);

CREATE UNIQUE INDEX "public"."uk_k8dic803e99gi49hvtny416la" ON "public"."t_url_filter" ("url" ASC);

CREATE INDEX "pg_catalog"."pg_trigger_tgconstraint_index" ON "pg_catalog"."pg_trigger" ("tgconstraint" ASC);

CREATE UNIQUE INDEX "pg_catalog"."pg_user_mapping_user_server_index" ON "pg_catalog"."pg_user_mapping" ("umuser" ASC, "umserver" ASC);

CREATE UNIQUE INDEX "pg_catalog"."pg_default_acl_role_nsp_obj_index" ON "pg_catalog"."pg_default_acl" ("defaclrole" ASC, "defaclnamespace" ASC, "defaclobjtype" ASC);

CREATE UNIQUE INDEX "pg_catalog"."pg_authid_rolname_index" ON "pg_catalog"."pg_authid" ("rolname" ASC);

CREATE UNIQUE INDEX "pg_catalog"."pg_policy_oid_index" ON "pg_catalog"."pg_policy" (null);

CREATE UNIQUE INDEX "public"."uk_pju8cmo3uddy73n2a8ath3vp3" ON "public"."t_article" ("menu_id" ASC);

CREATE UNIQUE INDEX "pg_catalog"."pg_am_oid_index" ON "pg_catalog"."pg_am" (null);

CREATE UNIQUE INDEX "public"."uk_eggo456jpj5xuej1jpo8b4395" ON "public"."t_gantt" ("title" ASC);

CREATE UNIQUE INDEX "pg_catalog"."pg_largeobject_loid_pn_index" ON "pg_catalog"."pg_largeobject" ("loid" ASC, "pageno" ASC);

CREATE UNIQUE INDEX "pg_catalog"."pg_extension_oid_index" ON "pg_catalog"."pg_extension" (null);

CREATE UNIQUE INDEX "pg_catalog"."pg_auth_members_member_role_index" ON "pg_catalog"."pg_auth_members" ("member" ASC, "roleid" ASC);

CREATE UNIQUE INDEX "pg_catalog"."pg_conversion_name_nsp_index" ON "pg_catalog"."pg_conversion" ("conname" ASC, "connamespace" ASC);

CREATE UNIQUE INDEX "pg_catalog"."pg_db_role_setting_databaseid_rol_index" ON "pg_catalog"."pg_db_role_setting" ("setdatabase" ASC, "setrole" ASC);

CREATE UNIQUE INDEX "pg_catalog"."pg_attribute_relid_attnum_index" ON "pg_catalog"."pg_attribute" ("attrelid" ASC, "attnum" ASC);

CREATE UNIQUE INDEX "pg_catalog"."pg_am_name_index" ON "pg_catalog"."pg_am" ("amname" ASC);

CREATE UNIQUE INDEX "pg_catalog"."pg_foreign_data_wrapper_oid_index" ON "pg_catalog"."pg_foreign_data_wrapper" (null);

CREATE UNIQUE INDEX "pg_catalog"."pg_constraint_oid_index" ON "pg_catalog"."pg_constraint" (null);

CREATE UNIQUE INDEX "pg_catalog"."pg_ts_dict_dictname_index" ON "pg_catalog"."pg_ts_dict" ("dictname" ASC, "dictnamespace" ASC);

CREATE UNIQUE INDEX "public"."ukpi7i8cjrb75qox4d7wfyfjjpb" ON "public"."t_user_role" ("user_id" ASC, "role_id" ASC);

CREATE UNIQUE INDEX "public"."t_role_pkey" ON "public"."t_role" ("id" ASC);

CREATE UNIQUE INDEX "pg_catalog"."pg_proc_oid_index" ON "pg_catalog"."pg_proc" (null);

CREATE UNIQUE INDEX "pg_catalog"."pg_amop_oid_index" ON "pg_catalog"."pg_amop" (null);

CREATE INDEX "public"."idxnk3llagr5tjnvhmvpomcxohj1" ON "public"."t_menu" ("alias" ASC);

CREATE UNIQUE INDEX "pg_catalog"."pg_extension_name_index" ON "pg_catalog"."pg_extension" ("extname" ASC);

CREATE UNIQUE INDEX "pg_catalog"."pg_foreign_server_name_index" ON "pg_catalog"."pg_foreign_server" ("srvname" ASC);

CREATE UNIQUE INDEX "pg_catalog"."pg_rewrite_oid_index" ON "pg_catalog"."pg_rewrite" (null);

CREATE UNIQUE INDEX "pg_catalog"."pg_ts_config_oid_index" ON "pg_catalog"."pg_ts_config" (null);

CREATE UNIQUE INDEX "pg_catalog"."pg_operator_oid_index" ON "pg_catalog"."pg_operator" (null);

CREATE UNIQUE INDEX "pg_catalog"."pg_tablespace_oid_index" ON "pg_catalog"."pg_tablespace" (null);

CREATE UNIQUE INDEX "pg_catalog"."pg_statistic_relid_att_inh_index" ON "pg_catalog"."pg_statistic" ("starelid" ASC, "staattnum" ASC, "stainherit" ASC);

CREATE UNIQUE INDEX "public"."uk_m5bu5erj83eubjsa1nyms0t89" ON "public"."t_user" ("phone" ASC);

CREATE UNIQUE INDEX "pg_catalog"."pg_database_datname_index" ON "pg_catalog"."pg_database" ("datname" ASC);

CREATE UNIQUE INDEX "pg_catalog"."pg_cast_source_target_index" ON "pg_catalog"."pg_cast" ("castsource" ASC, "casttarget" ASC);

CREATE UNIQUE INDEX "pg_catalog"."pg_transform_type_lang_index" ON "pg_catalog"."pg_transform" ("trftype" ASC, "trflang" ASC);

CREATE UNIQUE INDEX "pg_catalog"."pg_tablespace_spcname_index" ON "pg_catalog"."pg_tablespace" ("spcname" ASC);

CREATE UNIQUE INDEX "pg_catalog"."pg_transform_oid_index" ON "pg_catalog"."pg_transform" (null);

CREATE UNIQUE INDEX "pg_catalog"."pg_enum_typid_label_index" ON "pg_catalog"."pg_enum" ("enumtypid" ASC, "enumlabel" ASC);

CREATE UNIQUE INDEX "pg_catalog"."pg_auth_members_role_member_index" ON "pg_catalog"."pg_auth_members" ("roleid" ASC, "member" ASC);

CREATE UNIQUE INDEX "pg_catalog"."pg_opclass_oid_index" ON "pg_catalog"."pg_opclass" (null);

CREATE INDEX "public"."idxf4aiyxr8a1fpap7qjr6mg2ha7" ON "public"."t_menu" ("author" ASC);

CREATE UNIQUE INDEX "pg_catalog"."pg_authid_oid_index" ON "pg_catalog"."pg_authid" (null);

CREATE UNIQUE INDEX "pg_catalog"."pg_policy_polrelid_polname_index" ON "pg_catalog"."pg_policy" ("polrelid" ASC, "polname" ASC);

CREATE UNIQUE INDEX "pg_catalog"."pg_range_rngtypid_index" ON "pg_catalog"."pg_range" ("rngtypid" ASC);

CREATE UNIQUE INDEX "pg_catalog"."pg_collation_name_enc_nsp_index" ON "pg_catalog"."pg_collation" ("collname" ASC, "collencoding" ASC, "collnamespace" ASC);

ALTER TABLE "public"."t_permission" ADD CONSTRAINT "t_permission_pkey" PRIMARY KEY ("id");

ALTER TABLE "public"."t_user" ADD CONSTRAINT "t_user_pkey" PRIMARY KEY ("id");

ALTER TABLE "public"."t_role" ADD CONSTRAINT "t_role_pkey" PRIMARY KEY ("id");

ALTER TABLE "public"."t_url_filter" ADD CONSTRAINT "t_url_filter_pkey" PRIMARY KEY ("id");

ALTER TABLE "public"."t_shortcut" ADD CONSTRAINT "t_shortcut_pkey" PRIMARY KEY ("id");

ALTER TABLE "public"."t_article" ADD CONSTRAINT "t_article_pkey" PRIMARY KEY ("id");

ALTER TABLE "public"."t_gantt" ADD CONSTRAINT "t_gantt_pkey" PRIMARY KEY ("id");

ALTER TABLE "public"."t_system_info" ADD CONSTRAINT "t_system_info_pkey" PRIMARY KEY ("id");

ALTER TABLE "public"."t_menu" ADD CONSTRAINT "t_menu_pkey" PRIMARY KEY ("id");

ALTER TABLE "public"."t_navigation" ADD CONSTRAINT "t_navigation_pkey" PRIMARY KEY ("id");

ALTER TABLE "public"."t_gantt" ADD CONSTRAINT "fkblp3s700yq02r86m5im4nk4h9" FOREIGN KEY ("parent_id")
	REFERENCES "public"."t_gantt" ("id");

ALTER TABLE "public"."t_menu" ADD CONSTRAINT "fk4paxqyebl0scq6ur9osr0f56k" FOREIGN KEY ("parent_id")
	REFERENCES "public"."t_menu" ("id");

ALTER TABLE "public"."t_menu" ADD CONSTRAINT "fkq8ut9x74iv1mcp4vjgtqgg04i" FOREIGN KEY ("parent_id")
	REFERENCES "public"."t_menu" ("id");

ALTER TABLE "public"."t_role_permission" ADD CONSTRAINT "fk90j038mnbnthgkc17mqnoilu9" FOREIGN KEY ("role_id")
	REFERENCES "public"."t_role" ("id");

ALTER TABLE "public"."t_article" ADD CONSTRAINT "fk280ltiysaorjb82tibiqquq5q" FOREIGN KEY ("menu_id")
	REFERENCES "public"."t_menu" ("id");

ALTER TABLE "public"."t_gantt" ADD CONSTRAINT "fk56l1rns7hqabxbevgcxujdo6m" FOREIGN KEY ("parent_id")
	REFERENCES "public"."t_gantt" ("id");

ALTER TABLE "public"."t_user_permission" ADD CONSTRAINT "t_user_permission_permission_id_fkey" FOREIGN KEY ("permission_id")
	REFERENCES "public"."t_permission" ("id");

ALTER TABLE "public"."t_role_permission" ADD CONSTRAINT "fkjobmrl6dorhlfite4u34hciik" FOREIGN KEY ("permission_id")
	REFERENCES "public"."t_permission" ("id");

ALTER TABLE "public"."t_user_permission" ADD CONSTRAINT "t_user_permission_user_id_fkey" FOREIGN KEY ("user_id")
	REFERENCES "public"."t_user" ("id");

ALTER TABLE "public"."t_user_role" ADD CONSTRAINT "fkq5un6x7ecoef5w1n39cop66kl" FOREIGN KEY ("user_id")
	REFERENCES "public"."t_user" ("id");

ALTER TABLE "public"."t_user_role" ADD CONSTRAINT "fka9c8iiy6ut0gnx491fqx4pxam" FOREIGN KEY ("role_id")
	REFERENCES "public"."t_role" ("id");

ALTER TABLE "public"."t_navigation" ADD CONSTRAINT "fkk0477gwrac9n2dox90l8f45q3" FOREIGN KEY ("article_id")
	REFERENCES "public"."t_article" ("id");

