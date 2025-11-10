ALTER TABLE coordenadores
    ALTER COLUMN id TYPE BIGINT,
    ALTER COLUMN id SET DEFAULT nextval('coordenadores_id_seq'::regclass);

ALTER TABLE professores
    ALTER COLUMN id TYPE BIGINT,
    ALTER COLUMN id SET DEFAULT nextval('professores_id_seq'::regclass);

ALTER TABLE disciplinas
    ALTER COLUMN id TYPE BIGINT,
    ALTER COLUMN id SET DEFAULT nextval('disciplinas_id_seq'::regclass);