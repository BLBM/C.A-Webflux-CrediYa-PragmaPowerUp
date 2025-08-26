package co.com.bancolombia.r2dbc.user_repository;


class userReactiveTestAdapter {
/*
    private UserReactiveRepository userReactiveRepository;
    private TransactionalOperator txOperator;
    private UserReactiveRepositoryAdapter userReactiveRepositoryAdapter;
    private ObjectMapper mapper;


    @BeforeEach
    void setUp() {
        userReactiveRepository = Mockito.mock(UserReactiveRepository.class);
        txOperator = Mockito.mock(TransactionalOperator.class);
        mapper = Mockito.mock(ObjectMapper.class);

        when(mapper.map(any(), eq(User.class))).thenAnswer(invocation -> {
            return new User(
                    "Agustina",
                    "Mills",
                    "test@mail.com",
                    "123",
                    1,
                    "3000000000",
                    2000000.0,
                    "direccion",
                    new Date()
            );
        });

        userReactiveRepositoryAdapter = new UserReactiveRepositoryAdapter(userReactiveRepository, mapper, txOperator);
    }


    @Test
    void saveUser() {
        Date fecha = new Date();
        Usuario user = new Usuario(
                "agustina",
                "Mills",
                "test@gmail.com",
                "123456",
                1,
                "3155849871",
                2000000.0,
                "av 7 plaza la bendita",
                fecha
        );
        UserEntity userEntity = new UserEntity();

        when(userReactiveRepository.save(any())).thenReturn(Mono.just(userEntity));
        when(txOperator.transactional(any(Mono.class))).thenAnswer(invocation -> invocation.getArgument(0));
        StepVerifier.create(userReactiveRepositoryAdapter.save(user))
                .expectNextMatches(u -> u.getEmail().equals("test@mail.com"))
                .verifyComplete();

        verify(userReactiveRepository, times(1)).save(any());
    }

    @Test
    @DisplayName("guardar usuario falla y se propaga el error en la transacción")
    void saveUserTransactionalError() {
        Date fecha = new Date();
        Usuario user = new Usuario(
                "agustina",
                "Mills",
                "test@gmail.com",
                "123456",
                1,
                "3155849871",
                2000000.0,
                "av 7 plaza la bendita",
                fecha
        );


        when(userReactiveRepository.save(any()))
                .thenReturn(Mono.error(new RuntimeException("Error guardando en DB")));

        when(txOperator.transactional(any(Mono.class)))
                .thenAnswer(invocation -> invocation.getArgument(0));

        StepVerifier.create(userReactiveRepositoryAdapter.save(user))
                .expectErrorMatches(throwable ->
                        throwable instanceof RuntimeException &&
                                throwable.getMessage().equals("Error guardando en DB"))
                .verify();

        verify(txOperator, times(1)).transactional(any(Mono.class));
        verify(userReactiveRepository, times(1)).save(any());
    }
*/
}
