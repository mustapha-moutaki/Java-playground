<img width="2000" height="2000" alt="octocat-1758836374983" src="https://github.com/user-attachments/assets/1d2074ce-cff2-4d9b-8e8e-34d21a80edbc" />
<img width="896" height="896" alt="image" src="https://github.com/user-attachments/assets/1faa7fd4-a70d-442f-97b2-91dacff3db6b" />


```markdown
# 🎓 Guide Complet Spring Boot - 120+ Concepts Essentiels

> Checklist complète pour maîtriser Spring & Spring Boot

---

## 🟦 A. Core Spring (Théorie + Concepts Essentiels)

### 1. Injection de dépendances (DI) + IoC
- Pourquoi DI ? Pourquoi ne pas faire new ?
- @Autowired vs constructor injection
- Créer une classe Service et injecter Repository
- Field injection vs Setter injection vs Constructor injection

<details>
<summary>💡 Explication & Exemple</summary>

**Pourquoi DI ?** Pour découpler les classes. Si A crée B (`new B()`), A est fortement couplé à B. Avec l'IoC (Inversion of Control), Spring fournit B à A.
**Recommandation :** L'injection par constructeur est recommandée (permet l'immutabilité et facilite les tests unitaires).

```java
@Service
public class UserService {
    private final UserRepository userRepository;

    // Constructor Injection (Best Practice)
    // @Autowired est optionnel ici depuis Spring 4.3+
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }
}
```
</details>

### 2. @Component, @Service, @Repository — différences
- Quelle annotation utiliser et où ?
- Impact sur scan, exceptions, transactions
- Quand utiliser @Component générique ?

<details>
<summary>💡 Explication & Exemple</summary>

Techniquement, `@Service` et `@Repository` sont des alias de `@Component`, mais ils ajoutent de la sémantique :
*   **@Repository** : Capture les exceptions spécifiques à la base de données (PersistenceExceptionTranslationPostProcessor).
*   **@Service** : Indique la couche métier (pas de logique supplémentaire par défaut, mais sémantique).
*   **@Component** : Pour des beans utilitaires génériques (ex: un générateur de PDF).

```java
@Repository // Pour l'accès aux données
public class UserDao { ... }

@Service // Pour la logique métier
public class UserService { ... }

@Component // Pour un utilitaire
public class EmailValidator { ... }
```
</details>

### 3. @Configuration & @Bean
- Créer manuellement un bean (ex: ObjectMapper)
- Comparer avec @Component
- @Bean vs @Component - cas d'usage

<details>
<summary>💡 Explication & Exemple</summary>

Utilisez **@Configuration + @Bean** lorsque vous voulez configurer une classe tierce (dont vous n'avez pas le code source) ou pour une configuration centralisée. Utilisez **@Component** pour vos propres classes.

```java
@Configuration
public class AppConfig {
    
    @Bean
    public ObjectMapper objectMapper() {
        return new ObjectMapper(); // Configuration manuelle d'une lib externe
    }
}
```
</details>

### 4. ApplicationContext
- Comment Spring gère les beans ?
- Récupérer un bean depuis contexte
- BeanFactory vs ApplicationContext

<details>
<summary>💡 Explication & Exemple</summary>

`ApplicationContext` est l'interface centrale de Spring (le conteneur IoC). Il étend `BeanFactory` en ajoutant des fonctionnalités d'entreprise (i18n, événements, AOP).

```java
@Autowired
private ApplicationContext context;

void method() {
    // Récupération manuelle (rarement nécessaire si on utilise @Autowired)
    MyService service = context.getBean(MyService.class);
}
```
</details>

### 5. Bean scope
- singleton, prototype, request, session
- **Mise en situation** : service stateful cause problèmes en concurrence

<details>
<summary>💡 Explication & Exemple</summary>

*   **Singleton (défaut)** : Une seule instance par application. Attention aux variables de classe (problèmes de concurrence).
*   **Prototype** : Nouvelle instance à chaque injection.

```java
@Service
@Scope("prototype") // Ou ConfigurableBeanFactory.SCOPE_PROTOTYPE
public class StatefulService {
    // ...
}
```
</details>

### 6. Lifecycle des beans
- @PostConstruct / @PreDestroy
- InitializingBean / DisposableBean
- **Exemple** : initialisation d'un cache

<details>
<summary>💡 Explication & Exemple</summary>

Permet d'exécuter du code juste après la création du bean (et l'injection des dépendances) ou juste avant sa destruction.

```java
@Component
public class CacheService {
    
    @PostConstruct
    public void init() {
        System.out.println("Chargement du cache...");
    }

    @PreDestroy
    public void cleanup() {
        System.out.println("Fermeture des connexions...");
    }
}
```
</details>

### 7. Externalisation de configuration
- application.properties vs application.yml
- @Value / @ConfigurationProperties
- Valeurs par défaut et validation

<details>
<summary>💡 Explication & Exemple</summary>

`application.yml` est souvent préféré pour sa hiérarchie. `@ConfigurationProperties` est plus type-safe que `@Value`.

```java
// application.properties
// app.welcome-message=Bonjour

@Component
public class WelcomeService {
    
    @Value("${app.welcome-message:Default Hello}") // Valeur par défaut
    private String message;
}
```
</details>

### 8. Spring Profiles
- dev / test / prod
- Charger une config selon environnement
- @Profile sur beans et configurations

<details>
<summary>💡 Explication & Exemple</summary>

Permet d'activer des beans ou des configs selon l'environnement actif (défini via `spring.profiles.active`).

```java
@Service
@Profile("dev")
public class DevEmailService implements EmailService {
    // Mock d'envoi d'email
}

@Service
@Profile("prod")
public class ProdEmailService implements EmailService {
    // Vrai envoi SMTP
}
```
</details>

### 9. Logging
- Utilisation de SLF4J
- Niveaux de log (TRACE, DEBUG, INFO, WARN, ERROR)
- **Mise en situation** : loguer error avec stacktrace

<details>
<summary>💡 Explication & Exemple</summary>

Spring Boot utilise Logback par défaut via l'interface SLF4J. Avec Lombok, utilisez `@Slf4j`.

```java
@Slf4j // Génère: private static final Logger log = LoggerFactory.getLogger(ThisClass.class);
@Service
public class PaymentService {
    public void process() {
        try {
            log.info("Traitement paiement...");
        } catch (Exception e) {
            log.error("Erreur critique : ", e); // Passe l'exception en 2eme argument pour la stacktrace
        }
    }
}
```
</details>

### 10. Exceptions globales
- @ControllerAdvice / @RestControllerAdvice
- Créer un global exception handler
- @ExceptionHandler

<details>
<summary>💡 Explication & Exemple</summary>

Centralise la gestion des erreurs pour ne pas avoir de try-catch dans tous les controllers.

```java
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<String> handleNotFound(ResourceNotFoundException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
    }
}
```
</details>

---

## 🟩 B. Spring Boot (Fondamentaux + Mise en pratique)

### 11. Structure typique d'un projet Boot
- controller/service/repository/model/dto
- Package organization best practices

<details>
<summary>💡 Explication & Exemple</summary>

Structure classique en couches (Layered Architecture) :
```
com.example.app
 ├── config/       (Configs Sécurité, Swagger...)
 ├── controller/   (API Rest)
 ├── service/      (Logique métier)
 ├── repository/   (Interfaces JPA)
 ├── model/        (Entités JPA)
 ├── dto/          (Objets de transfert de données)
 └── Application.java
```
</details>

### 12. Démarrer une API REST
- Créer endpoint GET/POST/PUT/DELETE
- Différences entre @RestController / @Controller
- @RequestMapping et variantes

<details>
<summary>💡 Explication & Exemple</summary>

`@RestController` = `@Controller` + `@ResponseBody` (retourne du JSON par défaut).

```java
@RestController
@RequestMapping("/api/users")
public class UserController {

    @GetMapping("/{id}")
    public UserDto getUser(@PathVariable Long id) { ... }

    @PostMapping
    public UserDto createUser(@RequestBody UserDto user) { ... }
}
```
</details>

### 13. Validation côté backend
- @NotNull, @NotBlank, @Size, @Email, @Min, @Max
- @Valid et @Validated
- **Mise en situation** : refuser un payload invalide

<details>
<summary>💡 Explication & Exemple</summary>

Nécessite la dépendance `spring-boot-starter-validation`.

```java
public class UserDto {
    @NotBlank(message = "Le nom est obligatoire")
    private String name;
    
    @Email
    private String email;
}

// Dans le controller
public void create(@Valid @RequestBody UserDto dto) { ... }
```
</details>

### 14. DTO vs Entity
- Pourquoi séparer ?
- Mappage manuel ou avec ModelMapper
- MapStruct vs ModelMapper

<details>
<summary>💡 Explication & Exemple</summary>

Ne jamais exposer l'Entité JPA directement (problèmes de sécurité, boucles infinies JSON, couplage DB).
**Exemple MapStruct** (très performant car généré à la compilation) :

```java
@Mapper(componentModel = "spring")
public interface UserMapper {
    UserDto toDto(User user);
    User toEntity(UserDto dto);
}
```
</details>

### 15. Gestion des erreurs HTTP
- 404, 400, 500, 409, 403
- Créer ErrorResponse personnalisé
- ResponseEntity et status codes

<details>
<summary>💡 Explication & Exemple</summary>

Utilisez `ResponseEntity` pour contrôler le code retour.

```java
// Retourner 404
return ResponseEntity.notFound().build();

// Retourner 400 avec body
return ResponseEntity.badRequest().body(new ErrorResponse("Données invalides"));
```
</details>

### 16. Pagination
- PageRequest, Pageable
- Endpoint paginé
- Sorting et pagination combinés

<details>
<summary>💡 Explication & Exemple</summary>

JPA gère cela nativement.

```java
// Controller
@GetMapping
public Page<Product> getAll(Pageable pageable) {
    return repo.findAll(pageable);
}
// Appel: /api/products?page=0&size=10&sort=price,desc
```
</details>

### 17. File upload
- @RequestParam MultipartFile
- **Mise en situation** : sauvegarder image dans dossier
- Validation de fichier (taille, type)

<details>
<summary>💡 Explication & Exemple</summary>

```java
@PostMapping("/upload")
public String upload(@RequestParam("file") MultipartFile file) throws IOException {
    if (file.isEmpty()) throw new RuntimeException("Fichier vide");
    // Sauvegarde
    Files.copy(file.getInputStream(), Paths.get("uploads/" + file.getOriginalFilename()));
    return "OK";
}
```
</details>

### 18. File download
- Retourner un fichier PDF / image
- ResponseEntity avec Resource
- Content-Type et headers

<details>
<summary>💡 Explication & Exemple</summary>

```java
@GetMapping("/download/{filename}")
public ResponseEntity<Resource> download(@PathVariable String filename) {
    Resource file = new UrlResource(Paths.get("uploads/" + filename).toUri());
    return ResponseEntity.ok()
        .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + file.getFilename() + "\"")
        .body(file);
}
```
</details>

### 19. Global CORS config
- Autoriser frontend spécifique
- @CrossOrigin vs Configuration globale
- CORS preflight requests

<details>
<summary>💡 Explication & Exemple</summary>

Pour autoriser une app React/Angular sur un autre port.

```java
@Configuration
public class CorsConfig implements WebMvcConfigurer {
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOrigins("http://localhost:3000")
                .allowedMethods("GET", "POST", "PUT", "DELETE");
    }
}
```
</details>

### 20. Consuming APIs
- RestTemplate (legacy)
- WebClient (reactive)
- **Mise en situation** : appeler API externe (ex: météo)
- Gestion des erreurs et timeouts

<details>
<summary>💡 Explication & Exemple</summary>

**RestClient** (depuis Spring Boot 3.2) est le successeur moderne et synchrone de RestTemplate.

```java
RestClient restClient = RestClient.create();
String result = restClient.get()
    .uri("https://api.weather.com/v1/forecast")
    .retrieve()
    .body(String.class);
```
</details>

---

## 🟫 C. Spring Data JPA (Base de données)

### 21. Créer une entité
- @Entity, @Table, @Id, @GeneratedValue
- Stratégies de génération d'ID

<details>
<summary>💡 Explication & Exemple</summary>

`IDENTITY` est le plus courant pour MySQL/Postgres (auto-increment).

```java
@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    private String email;
}
```
</details>

### 22. Types importants
- String vs BigDecimal vs LocalDate
- **Mise en situation** : précision des prix (pourquoi BigDecimal)

<details>
<summary>💡 Explication & Exemple</summary>

*   **BigDecimal** : Pour l'argent. `double` ou `float` perdent de la précision (problèmes d'arrondis flottants).
*   **LocalDate** : Pour les dates sans heure (ex: anniversaire).

```java
private BigDecimal price; // Bon
private Double price;     // Mauvais pour la finance
```
</details>

### 23. Relations : OneToMany / ManyToOne
- **Exemple** : Category → Products
- Bidirectional vs Unidirectional

<details>
<summary>💡 Explication & Exemple</summary>

Le "propriétaire" de la relation est celui qui a la clé étrangère (ManyToOne).

```java
// Category
@OneToMany(mappedBy = "category")
private List<Product> products;

// Product (Propriétaire)
@ManyToOne
@JoinColumn(name = "category_id")
private Category category;
```
</details>

### 24. Relation ManyToMany
- Table intermédiaire
- **Mise en situation** : fournir DTO sans cycle infini (JsonIgnore, JsonManagedReference)

<details>
<summary>💡 Explication & Exemple</summary>

Nécessite `@JoinTable`. Attention aux boucles JSON (A contient B qui contient A...). Solution : Utiliser des DTOs ou `@JsonIgnore`.

```java
@ManyToMany
@JoinTable(name = "student_course", ... )
private List<Course> courses;
```
</details>

### 25. Cascade & orphanRemoval
- CascadeType.ALL, PERSIST, MERGE, REMOVE
- **Question** : Supprimer produit supprime images ? نعم/لا

<details>
<summary>💡 Explication & Exemple</summary>

*   **CascadeType.ALL** : Les actions sur le parent se répercutent sur l'enfant.
*   **orphanRemoval=true** : Si on retire un enfant de la liste Java, il est supprimé de la BDD.
*   *Question:* Si `orphanRemoval=true` ou `CascadeType.REMOVE` est mis, supprimer le produit supprime les images. Sinon, non.

```java
@OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
private List<Image> images;
```
</details>

### 26. JPQL vs SQL natif
- Créer query personnalisée
- Quand utiliser l'un ou l'autre

<details>
<summary>💡 Explication & Exemple</summary>

*   **JPQL** : Opère sur les entités (ex: `Select u from User u`). Portable entre les bases.
*   **Native** : SQL pur (ex: `Select * from users`). Lié à la DB spécifique.

```java
@Query("SELECT u FROM User u WHERE u.active = true") // JPQL
List<User> findActive();
```
</details>

### 27. @Query + paramètres
- Query dynamique avec LIKE
- @Param et paramètres nommés
- Queries natives avec nativeQuery=true

<details>
<summary>💡 Explication & Exemple</summary>

```java
@Query("SELECT p FROM Product p WHERE p.name LIKE %:keyword%")
List<Product> search(@Param("keyword") String keyword);
```
</details>

### 28. FetchType LAZY vs EAGER
- **Problème classique** : LazyInitializationException
- Quand utiliser LAZY vs EAGER
- @EntityGraph

<details>
<summary>💡 Explication & Exemple</summary>

*   **LAZY (défaut pour listes)** : Charge les données à la demande. Si session fermée -> Exception.
*   **EAGER** : Charge tout tout de suite (risque de lenteur).
*   **Solution** : `JOIN FETCH` ou `@EntityGraph` pour charger à la demande proprement.

```java
@OneToMany(fetch = FetchType.LAZY) // Recommandé
```
</details>

### 29. Transactions
- @Transactional
- Propagation (REQUIRED, REQUIRES_NEW, etc.)
- **Mise en situation** : opération bancaire (débit + crédit atomique)

<details>
<summary>💡 Explication & Exemple</summary>

Garantit l'atomicité (Tout réussit ou tout échoue/Rollback).

```java
@Transactional
public void transferMoney(Long from, Long to, BigDecimal amount) {
    debit(from, amount);
    credit(to, amount);
    // Si exception ici, débit et crédit sont annulés.
}
```
</details>

### 30. Pagination & sorting
- findAll(Pageable pageable)
- Créer des requêtes paginées custom

<details>
<summary>💡 Explication & Exemple</summary>

```java
// Repository
Page<User> findByCity(String city, Pageable pageable);

// Service usage
repo.findByCity("Paris", PageRequest.of(0, 10, Sort.by("name")));
```
</details>

### 31. Derived Query Methods
- findByName, findByEmailAndActive
- Conventions de nommage

<details>
<summary>💡 Explication & Exemple</summary>

Spring Data génère le SQL à partir du nom de la méthode.

```java
List<User> findByEmail(String email);
List<User> findByAgeGreaterThan(int age);
```
</details>

### 32. Specifications et Criteria API
- Recherche dynamique complexe
- Prédicates

<details>
<summary>💡 Explication & Exemple</summary>

Pour construire des requêtes dynamiques (ex: filtres optionnels).

```java
public static Specification<User> hasName(String name) {
    return (root, query, cb) -> cb.equal(root.get("name"), name);
}
// Usage: repo.findAll(Specification.where(hasName("John")).and(hasAge(20)));
```
</details>

---

## 🟧 D. Spring Boot Testing

### 33. Unit testing avec Mockito
- @Mock, @InjectMocks
- Mock repository
- Tester service (business logic)

<details>
<summary>💡 Explication & Exemple</summary>

Test pur Java, sans charger le contexte Spring (rapide).

```java
@ExtendWith(MockitoExtension.class)
class UserServiceTest {
    @Mock UserRepository repo;
    @InjectMocks UserService service;

    @Test
    void testFind() {
        when(repo.findById(1L)).thenReturn(Optional.of(new User()));
        // assertions...
    }
}
```
</details>

### 34. @SpringBootTest
- Tester tout le contexte
- @MockBean vs @Mock

<details>
<summary>💡 Explication & Exemple</summary>

Charge toute l'application. `@MockBean` remplace un bean réel du contexte Spring par un mock.

```java
@SpringBootTest
class IntegrationTest {
    @Autowired UserService service;
    @MockBean PaymentService paymentService; // Remplace le vrai bean
}
```
</details>

### 35. @WebMvcTest
- Tester controllers uniquement
- MockMvc

<details>
<summary>💡 Explication & Exemple</summary>

Ne charge que la couche web (Controllers, Filters), pas les Services ni la DB.

```java
@WebMvcTest(UserController.class)
class ControllerTest {
    @Autowired MockMvc mockMvc;
    @MockBean UserService service;
}
```
</details>

### 36. Test JPA avec @DataJpaTest
- H2 database en mémoire
- Tester repositories

<details>
<summary>💡 Explication & Exemple</summary>

Charge seulement la couche JPA et utilise une DB embarquée (H2) par défaut. Transactionnel (rollback fin de test).

```java
@DataJpaTest
class RepoTest {
    @Autowired UserRepository repo;
}
```
</details>

### 37. Test API avec MockMvc
- Tester une requête POST
- perform(), andExpect()

<details>
<summary>💡 Explication & Exemple</summary>

```java
mockMvc.perform(get("/api/users/1"))
       .andExpect(status().isOk())
       .andExpect(jsonPath("$.name").value("John"));
```
</details>

### 38. Test d'intégration
- Tester toute la stack
- TestRestTemplate

<details>
<summary>💡 Explication & Exemple</summary>

Simule un vrai client HTTP tapant sur l'app lancée sur un port aléatoire.

```java
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
class FullTest {
    @Autowired TestRestTemplate restTemplate;

    @Test
    void test() {
        ResponseEntity<String> resp = restTemplate.getForEntity("/api/users", String.class);
        assertThat(resp.getStatusCode()).isEqualTo(HttpStatus.OK);
    }
}
```
</details>

### 39. @Transactional dans les tests
- Rollback automatique

<details>
<summary>💡 Explication & Exemple</summary>

Dans les tests `@SpringBootTest` ou `@DataJpaTest`, `@Transactional` annule les modifications en base à la fin du test pour garder l'environnement propre.

```java
@Test
@Transactional
void testSave() {
    repo.save(new User());
    // À la fin, le user est supprimé (rollback)
}
```
</details>

---

## 🟨 E. Spring Security Basics

### 40. Basic Auth
- **Mise en situation** : protéger endpoint admin
- SecurityFilterChain

<details>
<summary>💡 Explication & Exemple</summary>

Auth simple avec header `Authorization: Basic base64(user:pass)`.

```java
http.authorizeHttpRequests(auth -> auth
    .requestMatchers("/admin/**").authenticated()
    .anyRequest().permitAll())
    .httpBasic(Customizer.withDefaults());
```
</details>

### 41. JWT — théorie débutant
- Qu'est-ce qu'un token signé
- Structure d'un JWT (header, payload, signature)

<details>
<summary>💡 Explication & Exemple</summary>

JWT (JSON Web Token) est stateless. Le serveur ne garde pas de session. Il vérifie la signature du token envoyé par le client pour savoir qui il est.
Format : `xxxxx.yyyyy.zzzzz` (Header . Claims/Data . Signature).
</details>

### 42. Créer filtre JWT simple
- Authentifier user via header Authorization
- OncePerRequestFilter

<details>
<summary>💡 Explication & Exemple</summary>

Un filtre qui intercepte chaque requête, extrait le token "Bearer xxx", le valide, et set l'authentification dans le contexte Spring.

```java
public class JwtFilter extends OncePerRequestFilter {
    @Override
    protected void doFilterInternal(HttpServletRequest req, ...) {
        // Logique d'extraction et validation JWT
        SecurityContextHolder.getContext().setAuthentication(auth);
        filterChain.doFilter(req, res);
    }
}
```
</details>

### 43. Password encoder
- BCryptPasswordEncoder
- Pourquoi ne jamais stocker password en clair

<details>
<summary>💡 Explication & Exemple</summary>

Il faut hacher les mots de passe. BCrypt inclut un "salt" aléatoire pour se protéger des Rainbow Tables.

```java
@Bean
public PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
}
// String hashed = encoder.encode("password123");
```
</details>

### 44. Roles & authorities
- ROLE_ADMIN / ROLE_USER
- Protéger routes avec @PreAuthorize
- hasRole() vs hasAuthority()

<details>
<summary>💡 Explication & Exemple</summary>

`hasRole('ADMIN')` attend automatiquement le préfixe `ROLE_ADMIN` dans la DB.

```java
@PreAuthorize("hasRole('ADMIN')")
@DeleteMapping("/{id}")
public void deleteUser(@PathVariable Long id) { ... }
```
</details>

### 45. Security Configuration
- SecurityFilterChain moderne (Spring Security 6+)
- Désactiver CSRF pour APIs stateless

<details>
<summary>💡 Explication & Exemple</summary>

```java
@Bean
public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
    http
        .csrf(csrf -> csrf.disable()) // Inutile pour API Stateless (JWT)
        .sessionManagement(sess -> sess.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
        .authorizeHttpRequests(auth -> auth.anyRequest().authenticated());
    return http.build();
}
```
</details>

### 46. UserDetailsService
- Implémenter loadUserByUsername
- Custom user authentication

<details>
<summary>💡 Explication & Exemple</summary>

Interface utilisée par Spring Security pour charger les users depuis VOTRE base de données.

```java
@Service
public class CustomUserDetailsService implements UserDetailsService {
    @Override
    public UserDetails loadUserByUsername(String username) {
        User user = repo.findByUsername(username)
             .orElseThrow(() -> new UsernameNotFoundException("Inconnu"));
        return new org.springframework.security.core.userdetails.User(
             user.getUsername(), user.getPassword(), user.getAuthorities());
    }
}
```
</details>

---

## 🟪 F. Streams Java (indispensable pour Spring)

### 47. map()
- Transformer liste de produits en liste de DTOs

<details>
<summary>💡 Explication & Exemple</summary>

```java
List<ProductDto> dtos = products.stream()
    .map(p -> new ProductDto(p.getName(), p.getPrice()))
    .collect(Collectors.toList());
```
</details>

### 48. filter()
- Filtrer clients actifs

<details>
<summary>💡 Explication & Exemple</summary>

```java
List<Client> activeClients = clients.stream()
    .filter(c -> c.isActive())
    .collect(Collectors.toList());
```
</details>

### 49. flatMap()
- Liste de catégories → liste de tous les produits
- **Exemple** : `categories.stream().flatMap(c -> c.getProducts().stream())`

<details>
<summary>💡 Explication & Exemple</summary>

Aplatit des structures imbriquées (List<List<Product>> -> List<Product>).

```java
List<Product> allProducts = categories.stream()
    .flatMap(c -> c.getProducts().stream())
    .collect(Collectors.toList());
```
</details>

### 50. collect()
- Collecter en List/Set/Map
- Collectors.toList(), toSet(), toMap()

<details>
<summary>💡 Explication & Exemple</summary>

Termine le stream et package le résultat.

```java
Map<Long, String> idToName = users.stream()
    .collect(Collectors.toMap(User::getId, User::getName));
```
</details>

### 51. sorted()
- Trier produits par prix
- Comparator.comparing()

<details>
<summary>💡 Explication & Exemple</summary>

```java
products.stream()
    .sorted(Comparator.comparing(Product::getPrice)) // Ascendant
    .collect(Collectors.toList());
```
</details>

### 52. reduce()
- Calculer total panier
- Somme, min, max

<details>
<summary>💡 Explication & Exemple</summary>

Réduit le stream à une seule valeur.

```java
BigDecimal total = items.stream()
    .map(Item::getPrice)
    .reduce(BigDecimal.ZERO, BigDecimal::add);
```
</details>

### 53. groupingBy()
- Grouper par catégorie
- Collectors.groupingBy()

<details>
<summary>💡 Explication & Exemple</summary>

Crée une Map où la clé est le critère de regroupement.

```java
Map<Category, List<Product>> byCategory = products.stream()
    .collect(Collectors.groupingBy(Product::getCategory));
```
</details>

### 54. anyMatch / allMatch / noneMatch
- Vérifier si un produit en stock

<details>
<summary>💡 Explication & Exemple</summary>

Retourne un booléen. Court-circuite dès que la réponse est trouvée.

```java
boolean hasStock = products.stream().anyMatch(p -> p.getStock() > 0);
```
</details>

### 55. Optional
- Gestion de null propre
- orElse(), orElseThrow(), ifPresent()

<details>
<summary>💡 Explication & Exemple</summary>

Évite `NullPointerException`.

```java
User user = repo.findById(1L)
    .orElseThrow(() -> new ResourceNotFoundException("User not found"));
```
</details>

### 56. Method reference
- ProductDto::new
- String::toUpperCase

<details>
<summary>💡 Explication & Exemple</summary>

Sucre syntaxique pour les lambdas. `x -> System.out.println(x)` devient `System.out::println`.

```java
names.stream().map(String::toUpperCase).collect(Collectors.toList());
```
</details>

### 57. peek()
- Debugging de streams

<details>
<summary>💡 Explication & Exemple</summary>

Permet de voir passer les éléments sans modifier le stream (utile pour logs).

```java
stream.filter(x -> x > 10)
      .peek(x -> System.out.println("Valeur filtrée: " + x))
      .collect(Collectors.toList());
```
</details>

### 58. distinct() et limit()
- Éliminer doublons
- Limiter résultats

<details>
<summary>💡 Explication & Exemple</summary>

```java
// Top 3 prix uniques
prices.stream().distinct().sorted().limit(3).collect(Collectors.toList());
```
</details>

---

## 🟫 G. API REST – Cas réels

### 59. CRUD complet pour une entité
- POST/GET/PUT/DELETE
- RESTful best practices

<details>
<summary>💡 Explication & Exemple</summary>

Standardiser les verbes :
*   `POST /users` (Créer)
*   `GET /users` (Lister)
*   `GET /users/1` (Lire un)
*   `PUT /users/1` (Mettre à jour complet)
*   `DELETE /users/1` (Supprimer)
</details>

### 60. Recherche par critères
- Query parameters (name, price, category, date)
- @RequestParam avec Optional

<details>
<summary>💡 Explication & Exemple</summary>

```java
@GetMapping("/search")
public List<Product> search(
    @RequestParam(required = false) String name,
    @RequestParam(required = false) BigDecimal minPrice) {
    // Logique de filtre dynamique
}
```
</details>

### 61. Gestion des statuts
- **Exemple** : ORDER → CREATED → PAID → SHIPPED
- Enum pour statuts

<details>
<summary>💡 Explication & Exemple</summary>

Utilisez des Enums Java et stockez-les sous forme de String en DB (`@Enumerated(EnumType.STRING)`).

```java
public enum OrderStatus { CREATED, PAID, SHIPPED, DELIVERED }
```
</details>

### 62. Export CSV/PDF
- Retourner données en différents formats
- Content-Type headers

<details>
<summary>💡 Explication & Exemple</summary>

Pour CSV, on écrit du texte dans la réponse avec le bon type MIME.

```java
response.setContentType("text/csv");
response.getWriter().write("id,name\n1,John");
```
</details>

### 63. Envoi email
- JavaMailSender
- Templates HTML pour emails

<details>
<summary>💡 Explication & Exemple</summary>

Starter : `spring-boot-starter-mail`.

```java
@Autowired JavaMailSender mailSender;

public void sendEmail(String to, String subject, String body) {
    SimpleMailMessage message = new SimpleMailMessage();
    message.setTo(to);
    message.setSubject(subject);
    message.setText(body);
    mailSender.send(message);
}
```
</details>

### 64. Scheduler
- @Scheduled pour tâche automatique
- Cron expressions
- **Exemple** : nettoyage quotidien de DB

<details>
<summary>💡 Explication & Exemple</summary>

Nécessite `@EnableScheduling` sur la classe principale.

```java
@Scheduled(cron = "0 0 0 * * ?") // Tous les jours à minuit
public void cleanUp() {
    System.out.println("Nettoyage...");
}
```
</details>

### 65. Webhook
- Recevoir callback externe
- Signature validation

<details>
<summary>💡 Explication & Exemple</summary>

Un endpoint POST ouvert que des services externes (Stripe, GitHub) appellent. Il faut vérifier un header de signature (HMAC) pour la sécurité.
</details>

### 66. Rate limiting
- Limiter nombre de requêtes par utilisateur
- Bucket4j

<details>
<summary>💡 Explication & Exemple</summary>

Empêche les abus (DDoS ou spam). Bucket4j est une librairie populaire Java pour l'algorithme "Token Bucket".
</details>

### 67. Versioning de l'API
- /api/v1 vs /api/v2
- Stratégies de versioning (URL, header, content negotiation)

<details>
<summary>💡 Explication & Exemple</summary>

La stratégie par URL est la plus simple et explicite.

```java
@RequestMapping("/api/v1/products")
public class ProductControllerV1 { ... }
```
</details>

### 68. HATEOAS (débutant)
- Links dans responses
- Spring HATEOAS

<details>
<summary>💡 Explication & Exemple</summary>

Hypermedia As The Engine Of Application State. Ajoute des liens (`_links`) dans le JSON pour dire au client quelles actions sont possibles (self, update, delete).

```json
{
  "name": "John",
  "_links": {
    "self": { "href": "http://api/users/1" }
  }
}
```
</details>

### 69. Request/Response logging
- Intercepteurs
- Filter pour logging

<details>
<summary>💡 Explication & Exemple</summary>

Un `CommonsRequestLoggingFilter` ou un filtre custom permet de tracer toutes les entrées/sorties HTTP pour le debug.
</details>

### 70. API Documentation
- SpringDoc OpenAPI (Swagger)
- @Operation, @ApiResponse

<details>
<summary>💡 Explication & Exemple</summary>

Ajoutez `springdoc-openapi-starter-webmvc-ui`.
Accès via `http://localhost:8080/swagger-ui.html`.

```java
@Operation(summary = "Obtenir un utilisateur")
@GetMapping("/{id}")
public User get(@PathVariable Long id) { ... }
```
</details>

---

## 🟬 H. Performance & Best Practices

### 71. Cache simple
- @Cacheable, @CacheEvict, @CachePut
- Cache sur un service

<details>
<summary>💡 Explication & Exemple</summary>

Nécessite `@EnableCaching`. Stocke le retour de méthode en mémoire.

```java
@Cacheable("products")
public Product getProduct(Long id) {
    return repo.findById(id).get(); // Ne s'exécute que si pas en cache
}
```
</details>

### 72. N+1 Problem
- Quand JPA fait trop de requêtes
- Solutions : @EntityGraph, JOIN FETCH

<details>
<summary>💡 Explication & Exemple</summary>

Si on charge 10 utilisateurs et qu'on boucle pour afficher leur adresse (Lazy), JPA fera 1 requête (users) + 10 requêtes (adresses).
**Solution :**
`@Query("SELECT u FROM User u JOIN FETCH u.address")`
</details>

### 73. DTO only
- Ne jamais exposer entity directement
- Projection

<details>
<summary>💡 Explication & Exemple</summary>

Pour la performance, ne récupérez que les colonnes nécessaires via des projections (Interfaces ou DTOs dans le constructeur JPQL).
</details>

### 74. Profiling et monitoring
- Spring Boot Actuator
- /actuator/health, /metrics

<details>
<summary>💡 Explication & Exemple</summary>

Ajoutez `spring-boot-starter-actuator`. Expose des endpoints pour voir l'état de l'app, la mémoire, les threads, etc.
</details>

### 75. Connection pool
- HikariCP tuning
- Configuration optimale

<details>
<summary>💡 Explication & Exemple</summary>

Spring Boot utilise HikariCP par défaut (le plus rapide). Important de configurer `maximum-pool-size` selon les capacités de la base de données.
</details>

### 76. Async processing
- @Async pour envoyer email
- @EnableAsync
- CompletableFuture

<details>
<summary>💡 Explication & Exemple</summary>

Exécute la méthode dans un thread séparé pour ne pas bloquer l'utilisateur.

```java
@Async
public void sendEmail() {
    // Tâche longue
}
```
</details>

### 77. Upload gros fichiers
- Configuration taille max
- spring.servlet.multipart.max-file-size

<details>
<summary>💡 Explication & Exemple</summary>

Dans `application.properties` :
```properties
spring.servlet.multipart.max-file-size=10MB
spring.servlet.multipart.max-request-size=10MB
```
</details>

### 78. Batch processing
- Traitement de 10k+ lignes
- @BatchSize

<details>
<summary>💡 Explication & Exemple</summary>

Pour insérer 1000 entités, ne faites pas 1000 INSERT. Activez le batch JDBC :
`spring.jpa.properties.hibernate.jdbc.batch_size=50`
</details>

### 79. Retry pattern
- Réessayer API externe en cas d'échec
- @Retryable

<details>
<summary>💡 Explication & Exemple</summary>

Nécessite `spring-retry`. Si l'API météo échoue, on réessaie 3 fois.

```java
@Retryable(maxAttempts = 3, backoff = @Backoff(delay = 2000))
public void callApi() { ... }
```
</details>

### 80. Timeouts et Circuit Breaker
- Configurer RestTemplate pour éviter blocage
- Resilience4j

<details>
<summary>💡 Explication & Exemple</summary>

Si un service B est en panne, le service A ne doit pas attendre indéfiniment (timeout) et doit arrêter d'appeler B temporairement (Circuit Breaker).
</details>

### 81. Database indexing
- Créer indexes pour performances
- @Index sur entités

<details>
<summary>💡 Explication & Exemple</summary>

Si vous faites souvent `findByEmail`, mettez un index sur la colonne email.

```java
@Table(indexes = @Index(columnList = "email"))
```
</details>

### 82. Query optimization
- EXPLAIN ANALYZE
- Éviter SELECT *

<details>
<summary>💡 Explication & Exemple</summary>

C'est une bonne pratique SQL. En JPA, cela revient à utiliser des DTOs pour ne sélectionner que les champs utiles, au lieu de charger l'entité entière.
</details>

---

## 🟧 I. Structure & Architecture

### 83. Architecture 3-layers
- controller/service/repository
- Séparation des responsabilités

<details>
<summary>💡 Explication & Exemple</summary>

*   **Controller** : Reçoit la requête HTTP, valide les DTOs.
*   **Service** : Logique métier, transactions.
*   **Repository** : Parle à la DB.
</details>

### 84. Clean architecture
- DTO / mappers
- Dependency inversion

<details>
<summary>💡 Explication & Exemple</summary>

L'objectif est de rendre le domaine métier indépendant des frameworks et de la base de données.
</details>

### 85. CQRS (débutant)
- Command Query Responsibility Segregation
- Séparation read/write

<details>
<summary>💡 Explication & Exemple</summary>

Pattern avancé : On utilise un modèle pour l'écriture (Command) et un modèle différent optimisé pour la lecture (Query).
</details>

### 86. Repository pattern
- Abstraction de la couche données

<details>
<summary>💡 Explication & Exemple</summary>

Spring Data JPA est une implémentation de ce pattern. Le code métier ne connaît pas le SQL, il appelle juste `save` ou `find`.
</details>

### 87. Service layer business rules
- Où mettre la logique métier

<details>
<summary>💡 Explication & Exemple</summary>

Le métier va dans le `@Service`. Le controller doit être "bête" (juste du routing). L'entité peut contenir de la logique propre à son état (Rich Domain Model).
</details>

### 88. Enum pour statuts
- Status d'une commande
- Best practices

<details>
<summary>💡 Explication & Exemple</summary>

Centralise les états possibles, évite les "magic strings".
</details>

### 89. Constants & utils
- Centraliser les constantes
- Classes utilitaires

<details>
<summary>💡 Explication & Exemple</summary>

Évitez de coder en dur "http://..." ou des nombres magiques. Mettez-les dans `static final` constants ou fichiers de config.
</details>

### 90. Error codes
- **Exemple** : USER_NOT_FOUND, INVALID_TOKEN
- Codes d'erreur standardisés

<details>
<summary>💡 Explication & Exemple</summary>

Retourner un code précis permet au Frontend d'afficher le bon message traduit sans parser le texte de l'erreur.
</details>

### 91. Response wrapper
- Toujours retourner un format uniforme
- ApiResponse<T>

<details>
<summary>💡 Explication & Exemple</summary>

Standardiser les réponses API :
```json
{
  "success": true,
  "data": { ... },
  "error": null
}
```
</details>

### 92. Mapper automatique
- ModelMapper vs MapStruct
- Performances et maintenabilité

<details>
<summary>💡 Explication & Exemple</summary>

**MapStruct** est préféré car il génère le code à la compilation (erreurs visibles tôt) et est rapide (pas de réflexion au runtime comme ModelMapper).
</details>

### 93. Builder pattern
- Lombok @Builder
- Construction d'objets complexes

<details>
<summary>💡 Explication & Exemple</summary>

```java
@Builder
public class User { ... }

// Usage
User u = User.builder().name("A").age(10).build();
```
</details>

### 94. Factory pattern
- Créer instances selon contexte

<details>
<summary>💡 Explication & Exemple</summary>

Utile si vous devez instancier un Service de paiement différent (Paypal vs Stripe) selon le choix de l'utilisateur.
</details>

---

## 🟥 J. Environnements & Déploiement

### 95. Maven
- Dépendances Spring Boot
- pom.xml structure
- Maven wrapper

<details>
<summary>💡 Explication & Exemple</summary>

Le `mvnw` (Wrapper) permet de lancer Maven sans l'installer sur la machine. Le `pom.xml` gère les versions via `spring-boot-starter-parent`.
</details>

### 96. Gradle (alternative)
- build.gradle
- Comparaison Maven vs Gradle

<details>
<summary>💡 Explication & Exemple</summary>

Gradle utilise Groovy ou Kotlin (plus concis que XML). Il est souvent plus rapide pour les builds incrémentaux.
</details>

### 97. Profiles avancés
- application-dev.yml / application-prod.yml
- Variables d'environnement par profile

<details>
<summary>💡 Explication & Exemple</summary>

Surchargez les valeurs par défaut.
`java -jar app.jar --spring.profiles.active=prod`
Chargera `application-prod.yml`.
</details>

### 98. Docker
- Dockerfile pour Spring Boot
- docker-compose (MySQL + app)
- Multi-stage builds

<details>
<summary>💡 Explication & Exemple</summary>

```dockerfile
FROM eclipse-temurin:17-jdk-alpine
COPY target/*.jar app.jar
ENTRYPOINT ["java","-jar","/app.jar"]
```
</details>

### 99. Actuator endpoints
- /health, /metrics, /info
- Custom health indicators

<details>
<summary>💡 Explication & Exemple</summary>

Indispensable pour Kubernetes (liveness/readiness probes).
`GET /actuator/health` retourne `{"status": "UP"}`.
</details>

### 100. Monitoring et observabilité
- Prometheus + Grafana
- Logs centralisés

<details>
<summary>💡 Explication & Exemple</summary>

Actuator peut exposer des métriques au format Prometheus (`/actuator/prometheus`) que Grafana peut visualiser (Graphiques CPU, Requêtes/sec).
</details>

### 101. Build jar exécutable
- mvn clean package
- java -jar application.jar

<details>
<summary>💡 Explication & Exemple</summary>

Spring Boot package toutes les dépendances (Tomcat inclus) dans un "Fat Jar" autonome.
</details>

### 102. Variables d'environnement
- DB_URL, JWT_SECRET
- Externaliser configs sensibles

<details>
<summary>💡 Explication & Exemple</summary>

Dans `application.properties`: `spring.datasource.password=${DB_PASSWORD}`.
Au lancement : `export DB_PASSWORD=secret && java -jar app.jar`.
</details>

### 103. CI/CD (débutant)
- GitHub Actions basique
- Pipeline build → test → deploy

<details>
<summary>💡 Explication & Exemple</summary>

Automatiser les tests à chaque push git. Fichier `.github/workflows/maven.yml`.
</details>

### 104. Testcontainers
- Lancer DB réelle en test
- Tests d'intégration avec Docker

<details>
<summary>💡 Explication & Exemple</summary>

Au lieu de H2 (in-memory), Testcontainers lance un vrai Docker Postgres pendant les tests pour être iso-prod.
</details>

### 105. Cloud deployment
- Héberger API sur Render / Railway / Heroku
- AWS Elastic Beanstalk basics

<details>
<summary>💡 Explication & Exemple</summary>

Ces plateformes détectent le `pom.xml` ou le `Dockerfile` et déploient l'application automatiquement.
</details>

### 106. SSL/TLS
- HTTPS configuration
- Certificats Let's Encrypt

<details>
<summary>💡 Explication & Exemple</summary>

En prod, le SSL est souvent géré par un Reverse Proxy (Nginx) ou le Load Balancer Cloud, pas directement par Spring Boot (bien que possible via `server.ssl.*`).
</details>

---

## 🟦 K. Concepts Avancés (mais utiles)

### 107. Soft delete
- Ajouter champ deleted = true
- @Where, @SQLDelete

<details>
<summary>💡 Explication & Exemple</summary>

Au lieu de supprimer la ligne (`DELETE`), on la marque comme supprimée.

```java
@SQLDelete(sql = "UPDATE user SET deleted = true WHERE id=?")
@Where(clause = "deleted=false")
public class User { ... }
```
</details>

### 108. Audit automatique
- createdAt / updatedAt
- @CreatedDate, @LastModifiedDate
- @EntityListeners

<details>
<summary>💡 Explication & Exemple</summary>

Nécessite `@EnableJpaAuditing` et la classe `AuditingEntityListener`.

```java
@EntityListeners(AuditingEntityListener.class)
public class BaseEntity {
    @CreatedDate
    private LocalDateTime createdAt;
}
```
</details>

### 109. Event-driven architecture
- ApplicationEvent
- @EventListener
- Publish/Subscribe pattern

<details>
<summary>💡 Explication & Exemple</summary>

Découpler les services. User créé -> Événement -> Envoi Email.

```java
// Publisher
eventPublisher.publishEvent(new UserCreatedEvent(user));

// Listener
@EventListener
public void handle(UserCreatedEvent event) { sendWelcomeEmail(event.getUser()); }
```
</details>

### 110. AOP (Aspect-Oriented Programming)
- Loguer durée d'exécution d'un service
- @Aspect, @Around, @Before, @After

<details>
<summary>💡 Explication & Exemple</summary>

Séparer les préoccupations transverses (Logs, Sécurité) du code métier.

```java
@Around("@annotation(LogExecutionTime)")
public Object logTime(ProceedingJoinPoint joinPoint) throws Throwable {
    long start = System.currentTimeMillis();
    Object proceed = joinPoint.proceed();
    long executionTime = System.currentTimeMillis() - start;
    log.info("Durée: " + executionTime + "ms");
    return proceed;
}
```
</details>

### 111. Multimodule Maven
- Séparer api, core, persistence
- Parent POM

<details>
<summary>💡 Explication & Exemple</summary>

Pour les gros projets, évite le code spaghetti. Le module `api` dépend de `core`, qui dépend de `persistence`.
</details>

### 112. Handling concurrency
- Optimistic Lock (@Version)
- Pessimistic Lock

<details>
<summary>💡 Explication & Exemple</summary>

**Optimistic** : Ajoute une colonne version. Si deux users sauvent en même temps, le 2ème échoue (Exception).

```java
@Version
private Integer version;
```
</details>

### 113. Large DTO et pagination
- Éviter heavy payload
- DTO projection

<details>
<summary>💡 Explication & Exemple</summary>

Si votre entité a 50 champs mais que la liste n'en affiche que 3, créez un `UserSummaryDto` léger.
</details>

### 114. Logs structurés
- JSON logs pour parsing
- Logstash format

<details>
<summary>💡 Explication & Exemple</summary>

Au lieu de texte brut, loguer en JSON permet aux outils (ELK, Datadog) d'indexer les champs (userId, severity) facilement.
</details>

### 115. API throttling avancé
- Limiter 10 requêtes / minute par user
- Redis pour rate limiting

<details>
<summary>💡 Explication & Exemple</summary>

Utiliser Redis permet de partager le compteur de requêtes entre plusieurs instances de l'API (Rate limiting distribué).
</details>

### 116. Architecture e-commerce complète
- users, orders, payments, products
- Microservices communication

<details>
<summary>💡 Explication & Exemple</summary>

Passer du Monolithe aux Microservices : chaque domaine a sa propre DB et communique via HTTP (Feign) ou Events (Kafka).
</details>

### 117. WebSocket
- Communication temps réel
- @EnableWebSocket
- STOMP messaging

<details>
<summary>💡 Explication & Exemple</summary>

Pour un chat ou des notifications live.

```java
@MessageMapping("/chat")
@SendTo("/topic/messages")
public Message send(Message message) { return message; }
```
</details>

### 118. Message Queue
- RabbitMQ / Kafka basics
- Async communication entre services

<details>
<summary>💡 Explication & Exemple</summary>

Pour traiter des charges lourdes de manière asynchrone et fiable. Spring Cloud Stream facilite l'intégration.
</details>

### 119. Redis integration
- Cache distribué
- Session management

<details>
<summary>💡 Explication & Exemple</summary>

`spring-session-data-redis` permet de stocker les sessions HTTP dans Redis. Si le serveur redémarre, les utilisateurs restent connectés.
</details>

### 120. GraphQL avec Spring Boot
- Alternative à REST
- Spring for GraphQL

<details>
<summary>💡 Explication & Exemple</summary>

Le client demande exactement les champs qu'il veut. Évite Over-fetching et Under-fetching.

```graphql
query {
  user(id: 1) {
    name
    posts { title }
  }
}
```
</details>

---

## 📚 Concepts supplémentaires essentiels

### 121. @Qualifier
- Choisir entre plusieurs beans du même type

<details>
<summary>💡 Explication & Exemple</summary>

Si vous avez deux implémentations de `PaymentService` (Stripe, Paypal), dites à Spring laquelle injecter.

```java
@Autowired
@Qualifier("stripePaymentService")
private PaymentService paymentService;
```
</details>

### 122. @Primary
- Bean par défaut

<details>
<summary>💡 Explication & Exemple</summary>

Indique le bean prioritaire si plusieurs existent, pour éviter l'ambiguïté sans utiliser `@Qualifier` partout.

```java
@Primary
@Service
public class DefaultPaymentService implements PaymentService { ... }
```
</details>

### 123. @Lazy
- Lazy initialization de beans

<details>
<summary>💡 Explication & Exemple</summary>

Le bean ne sera créé et injecté que lorsqu'il sera utilisé pour la première fois, pas au démarrage de l'app (accélère le démarrage).
</details>

### 124. Custom annotations
- Créer vos propres annotations
- Meta-annotations

<details>
<summary>💡 Explication & Exemple</summary>

Créer `@CurrentUserId` pour injecter l'ID du user connecté dans les contrôleurs.

```java
@Target(ElementType.PARAMETER)
@AuthenticationPrincipal
public @interface CurrentUser {}
```
</details>

### 125. Request/Response interceptors
- HandlerInterceptor
- Modifier requêtes/réponses

<details>
<summary>💡 Explication & Exemple</summary>

Intercepte la requête HTTP avant qu'elle n'arrive au Controller (ex: vérifier un token API key header manuellement).
</details>

---

## 🎯 Checklist finale

- [ ] Maîtriser DI et IoC
- [ ] Comprendre cycle de vie des beans
- [ ] Créer API REST complète
- [ ] Gérer relations JPA
- [ ] Écrire tests unitaires et intégration
- [ ] Implémenter sécurité (JWT)
- [ ] Optimiser performances (cache, N+1)
- [ ] Déployer sur cloud
- [ ] Monitoring et logging
- [ ] Architecture propre (3-layers)




# 🔐 Complete Spring Security Guide - 100+ Essential Concepts

> Comprehensive checklist to master Spring Security from basics to advanced

---

## 🟦 A. Spring Security Fundamentals

### 1. Core Concepts
- What is Spring Security?
- Authentication vs Authorization
- Principal, Credentials, and Authorities
- Security Filter Chain architecture
- How Spring Security works internally

### 2. Initial Setup
- Add Spring Security dependency
- Default behavior when adding Spring Security
- Default username and password
- Disable default security (not recommended)

### 3. SecurityFilterChain (Spring Security 6+)
- What is SecurityFilterChain?
- Difference between old WebSecurityConfigurerAdapter (deprecated) and new approach
- Create basic SecurityFilterChain bean
- Configure HTTP security

### 4. Authentication Architecture
- AuthenticationManager
- AuthenticationProvider
- ProviderManager
- Authentication object

---

## 🟩 B. Basic Authentication Methods

### 5. In-Memory Authentication
- Configure users in memory
- InMemoryUserDetailsManager
- **Use case**: Development and testing

### 6. JDBC Authentication
- Store users in database
- JdbcUserDetailsManager
- Default schema for users and authorities

### 7. Custom UserDetailsService
- Implement UserDetailsService interface
- loadUserByUsername() method
- Return UserDetails object
- **Use case**: Custom user entity from database

### 8. UserDetails Interface
- Username, password, authorities
- Account expiration, locking, credentials expiration
- isEnabled() flag

### 9. User Entity vs UserDetails
- Separate User entity from UserDetails
- Implement UserDetails or use adapter
- **Best practice**: Create UserDetailsImpl wrapper

---

## 🟫 C. Password Management

### 10. Password Encoding
- Why never store plain text passwords
- PasswordEncoder interface
- BCryptPasswordEncoder (recommended)
- Argon2PasswordEncoder, SCryptPasswordEncoder

### 11. Password Encoding Configuration
- Configure PasswordEncoder bean
- Encode password on registration
- Password strength validation

### 12. Password Reset Flow
- Generate reset token
- Send reset email
- Validate token and expiration
- Update password securely

### 13. Password History
- Prevent password reuse
- Store password history
- Validate against previous passwords

---

## 🟧 D. Authorization & Access Control

### 14. Roles vs Authorities
- Difference between roles and authorities
- ROLE_ prefix convention
- GrantedAuthority interface

### 15. Method Security Annotations
- @PreAuthorize
- @PostAuthorize
- @Secured
- @RolesAllowed
- Enable with @EnableMethodSecurity

### 16. URL-based Authorization
- hasRole() vs hasAuthority()
- hasAnyRole() and hasAnyAuthority()
- permitAll() and denyAll()
- authenticated() and anonymous()

### 17. SpEL in Security
- Spring Expression Language for access control
- hasRole('ADMIN')
- #authentication.name
- Complex expressions

### 18. Role Hierarchy
- Configure role hierarchy
- ROLE_ADMIN > ROLE_USER
- Inherit permissions

### 19. Custom Authorization Logic
- Custom AccessDecisionVoter
- Custom PermissionEvaluator
- Complex business logic authorization

---

## 🟨 E. JWT (JSON Web Tokens)

### 20. JWT Basics
- What is JWT?
- JWT structure (header, payload, signature)
- Stateless authentication
- JWT vs Session-based authentication

### 21. JWT Components
- Header (algorithm, type)
- Payload (claims: sub, exp, iat)
- Signature (secret key)
- How JWT is verified

### 22. Generate JWT Token
- Create JWT on successful login
- Add claims (user id, roles, email)
- Set expiration time
- Sign with secret key

### 23. JWT Libraries
- jjwt (io.jsonwebtoken)
- java-jwt (auth0)
- Configure dependencies

### 24. JWT Authentication Filter
- Extend OncePerRequestFilter
- Extract token from Authorization header
- Validate token signature and expiration
- Set authentication in SecurityContext

### 25. JWT Service/Utility
- generateToken()
- validateToken()
- extractUsername()
- extractClaims()
- isTokenExpired()

### 26. JWT Response
- Return token on login
- Token format: Bearer {token}
- Include refresh token

### 27. JWT Expiration
- Access token (short-lived: 15min - 1h)
- Refresh token (long-lived: days/weeks)
- Handle expired tokens

### 28. Refresh Token Flow
- Store refresh token securely
- Endpoint to refresh access token
- Validate refresh token
- Issue new access token

### 29. JWT Best Practices
- Use HTTPS only
- Short expiration for access tokens
- Secure secret key storage
- Token revocation strategy
- Claims validation

### 30. JWT Security Concerns
- XSS attacks (where to store token)
- Token theft
- Token in localStorage vs httpOnly cookie
- Logout and token invalidation

---

## 🟪 F. OAuth2 & Social Login

### 31. OAuth2 Basics
- What is OAuth2?
- Authorization Code Flow
- Client Credentials Flow
- Resource Owner, Client, Authorization Server

### 32. OAuth2 Login
- Configure OAuth2 login
- Google authentication
- GitHub authentication
- Facebook authentication

### 33. OAuth2 Client Configuration
- application.yml configuration
- client-id and client-secret
- redirect-uri
- scopes

### 34. Custom OAuth2 User Service
- OAuth2UserService interface
- Extract user info from provider
- Map to your User entity
- Save user on first login

### 35. OAuth2 Authorization Server
- Build your own OAuth2 server
- Spring Authorization Server
- Issue tokens to clients

---

## 🟫 G. CORS Configuration

### 36. CORS Basics
- What is CORS?
- Preflight requests (OPTIONS)
- Why CORS errors occur

### 37. Global CORS Configuration
- Configure CORS in SecurityFilterChain
- Allow specific origins
- Allow credentials
- Allowed methods and headers

### 38. @CrossOrigin Annotation
- Per-controller or per-method CORS
- When to use annotation vs global config

### 39. CORS with JWT
- Handle preflight requests
- Authorization header in CORS

---

## 🟬 H. CSRF Protection

### 40. CSRF Basics
- What is CSRF attack?
- CSRF token mechanism
- When CSRF protection is needed

### 41. Disable CSRF
- For stateless APIs (JWT)
- csrf().disable()
- When is it safe to disable?

### 42. CSRF with Session-based Auth
- Enable CSRF for form-based login
- Include CSRF token in forms
- CSRF token in AJAX requests

---

## 🟧 I. Session Management

### 43. Session-based Authentication
- How sessions work in Spring Security
- JSESSIONID cookie
- Session in server memory

### 44. Session Configuration
- Session creation policy
- ALWAYS, IF_REQUIRED, NEVER, STATELESS
- Maximum sessions per user

### 45. Concurrent Session Control
- Limit user to one session
- sessionManagement().maximumSessions(1)
- Handle multiple login attempts

### 46. Session Fixation Protection
- What is session fixation?
- changeSessionId() strategy
- Protect against session hijacking

### 47. Remember Me
- Persistent login
- rememberMe() configuration
- Token-based remember me

---

## 🟥 J. Advanced Authentication

### 48. Multi-factor Authentication (MFA)
- TOTP (Time-based One-Time Password)
- SMS verification
- Email verification codes
- Google Authenticator integration

### 49. Account Verification
- Email verification on registration
- Generate verification token
- Verify token and activate account

### 50. Account Lockout
- Lock account after failed login attempts
- Temporary vs permanent lockout
- Unlock mechanisms

### 51. Login Attempt Tracking
- Track failed login attempts
- Store in database or cache
- Reset counter on successful login

### 52. Custom Authentication Provider
- Implement AuthenticationProvider
- Custom authentication logic
- Integrate external authentication service

### 53. Multiple Authentication Providers
- Configure multiple providers
- Try each provider in sequence
- Fallback authentication

---

## 🟦 K. Security Filters

### 54. Security Filter Chain Order
- Understanding filter order
- Common filters and their positions
- Custom filter placement

### 55. OncePerRequestFilter
- Extend OncePerRequestFilter
- Ensure filter runs once per request
- **Use case**: JWT validation filter

### 56. Custom Security Filters
- Create custom filter
- Add filter to chain
- addFilterBefore(), addFilterAfter(), addFilterAt()

### 57. Exception Handling in Filters
- Handle exceptions in security filters
- AuthenticationEntryPoint
- AccessDeniedHandler

---

## 🟩 L. Security Events & Auditing

### 58. Authentication Events
- AuthenticationSuccessEvent
- AuthenticationFailureEvent
- Listen with @EventListener

### 59. Authorization Events
- AuthorizationSuccessEvent
- AuthorizationFailureEvent
- Track access attempts

### 60. Audit Logging
- Log security events
- Who accessed what and when
- Store audit logs in database

### 61. Security Context
- SecurityContextHolder
- Get current authenticated user
- Set authentication programmatically

---

## 🟫 M. Testing Security

### 62. Test with MockUser
- @WithMockUser annotation
- Mock authentication in tests
- Test with different roles

### 63. Test with Custom User
- @WithUserDetails
- Load real user from UserDetailsService
- Integration tests

### 64. Test Security Configuration
- Test protected endpoints return 401/403
- Test public endpoints accessible
- MockMvc security setup

### 65. Test Method Security
- Test @PreAuthorize methods
- Test authorization logic
- Mock SecurityContext

---

## 🟧 N. Microservices Security

### 66. Service-to-Service Authentication
- JWT for inter-service communication
- API keys
- Mutual TLS (mTLS)

### 67. API Gateway Security
- Centralized authentication
- Token validation at gateway
- Route-based authorization

### 68. Token Propagation
- Pass JWT between services
- RestTemplate with interceptors
- Feign client with JWT

### 69. Distributed Session
- Spring Session with Redis
- Share session across services
- Session replication

---

## 🟨 O. Additional Security Features

### 70. Content Security Policy (CSP)
- Configure CSP headers
- Prevent XSS attacks
- Script sources whitelisting

### 71. Security Headers
- X-Frame-Options (clickjacking)
- X-Content-Type-Options
- X-XSS-Protection
- Strict-Transport-Security (HSTS)

### 72. Rate Limiting
- Limit login attempts per IP
- API rate limiting
- Bucket4j integration

### 73. IP Whitelisting
- Allow requests only from specific IPs
- hasIpAddress() expression
- Dynamic IP filtering

### 74. User Impersonation
- Admin impersonate user
- SwitchUserFilter
- Security considerations

### 75. Logout Handling
- Custom logout URL
- Logout success handler
- Clear security context and session
- Invalidate JWT (blacklist)

### 76. Custom Login Page
- Create custom login form
- Configure formLogin()
- Login success and failure handlers

### 77. Custom Error Pages
- 401 Unauthorized page
- 403 Forbidden page
- Custom error responses for APIs

---

## 🟪 P. Security Best Practices

### 78. Principle of Least Privilege
- Grant minimum necessary permissions
- Default deny, explicit allow
- Role-based access control

### 79. Input Validation
- Validate all user inputs
- Prevent SQL injection
- Prevent XSS

### 80. Sensitive Data Protection
- Encrypt sensitive data
- Don't log passwords
- Mask sensitive info in logs

### 81. Dependency Security
- Keep dependencies updated
- Scan for vulnerabilities
- Use OWASP Dependency Check

### 82. HTTPS Only
- Enforce HTTPS
- Redirect HTTP to HTTPS
- HSTS header

### 83. Secure Configuration
- Externalize secrets
- Environment variables
- Vault integration

### 84. Security Monitoring
- Monitor failed login attempts
- Alert on suspicious activity
- Security dashboards

---

## 🟫 Q. Common Security Vulnerabilities

### 85. SQL Injection Prevention
- Use parameterized queries
- JPA/Hibernate protection
- Never concatenate SQL

### 86. XSS Prevention
- Escape output
- Content Security Policy
- Input sanitization

### 87. CSRF Prevention
- Enable CSRF for stateful apps
- Validate CSRF tokens
- SameSite cookies

### 88. Insecure Deserialization
- Validate serialized data
- Whitelist allowed classes
- Avoid deserialization when possible

### 89. Security Misconfiguration
- Remove default accounts
- Disable debug in production
- Proper error handling

### 90. Broken Authentication
- Strong password policies
- Account lockout
- Session management

---

## 🟥 R. Real-World Scenarios

### 91. E-commerce Security
- Secure payment processing
- PCI DSS compliance basics
- Customer data protection

### 92. Banking Application Security
- Transaction authorization
- Strong customer authentication
- Fraud detection

### 93. Multi-tenant Security
- Tenant isolation
- Data segregation
- Tenant-specific permissions

### 94. Admin Dashboard Security
- Separate admin authentication
- Admin role hierarchy
- Audit all admin actions

### 95. Mobile API Security
- API keys for mobile apps
- Certificate pinning
- Secure token storage

### 96. Public API Security
- API keys management
- Rate limiting per client
- Usage tracking

---

## 🟧 S. Advanced Topics

### 97. Custom SecurityContext
- Implement custom security context
- Thread-local storage
- Async security context propagation

### 98. Method-level Encryption
- Encrypt specific fields
- @Encrypted annotation (custom)
- Database encryption

### 99. Biometric Authentication
- Integrate biometric verification
- Fingerprint, face recognition
- WebAuthn support

### 100. Zero Trust Architecture
- Never trust, always verify
- Continuous authentication
- Micro-segmentation

### 101. Security in GraphQL
- Secure GraphQL endpoints
- Query complexity limits
- Field-level authorization

### 102. WebSocket Security
- Secure WebSocket connections
- Authentication in WebSocket
- Authorization per message

### 103. Reactive Security
- Spring Security Reactive
- WebFlux security
- Reactive SecurityContext

### 104. GraalVM Native Image Security
- Security in native images
- Reflection configuration
- Performance considerations

### 105. Compliance & Regulations
- GDPR compliance
- Data retention policies
- Right to be forgotten
- Privacy by design

---

## 🎯 Final Checklist

- [ ] Understand authentication vs authorization
- [ ] Implement custom UserDetailsService
- [ ] Configure password encoding (BCrypt)
- [ ] Implement JWT authentication
- [ ] Refresh token mechanism
- [ ] Role-based access control
- [ ] Method security with @PreAuthorize
- [ ] Handle security exceptions properly
- [ ] Configure CORS for frontend
- [ ] OAuth2 social login
- [ ] Security testing
- [ ] Audit logging
- [ ] Rate limiting
- [ ] Security headers configuration
- [ ] Understand common vulnerabilities

---

**🔐 Master Spring Security and build secure applications!**

---

**🚀 Bon apprentissage !**
```
