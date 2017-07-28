package com.comarch.fbi.internship.todolg.model.repositories.common;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.support.SimpleJpaRepository;
import org.springframework.data.util.ClassTypeInformation;
import org.springframework.data.util.TypeInformation;

import com.querydsl.core.types.EntityPath;
import com.querydsl.core.types.Predicate;
import com.querydsl.jpa.impl.JPAQuery;

/**
 * Bazowa klasa dla repozytorium typu CRUD.
 *
 * @param <T>  typ encji
 * @param <ID> typ identyfikatora encji
 */
public class BaseJpaRepository<T, ID extends Serializable> {

    private final EntityPathResolver<T> pathResolver = new EntityPathResolver<>();
    @PersistenceContext
    private EntityManager em;
    private SimpleJpaRepository<T, ID> jpaRepository;
    private Class<T> domainType;
    private EntityPath<T> path;

    /**
     * Konstruktor.
     */
    @SuppressWarnings("unchecked")
    public BaseJpaRepository() {
        this.domainType = (Class<T>) resolveDomainType(this.getClass());
        this.path = pathResolver.createPath(domainType);
    }

    private Class<?> resolveDomainType(final Class<?> repositoryInterface) {
        TypeInformation<?> information = ClassTypeInformation.from(repositoryInterface);
        List<TypeInformation<?>> arguments = information.
                getSuperTypeInformation(BaseJpaRepository.class).getTypeArguments();

        if (arguments.isEmpty() || arguments.get(0) == null) {
            throw new IllegalArgumentException(
                    String.format("Could not resolve domain type of %s!", repositoryInterface));
        }

        return arguments.get(0).getType();
    }

    /**
     * Inicjalizacja obiektu.
     */
    @SuppressWarnings("unchecked")
    @PostConstruct
    public void init() {
        jpaRepository = new SimpleJpaRepository<>(this.domainType, em);
    }

    /**
     * Znajduje pojedynczą encję spełniające predykat.
     *
     * @param predicate predykat
     *
     * @return Pojedyncza encja spełniająca predykat.
     */
    public T findOne(final Predicate predicate) {
        return createQuery(predicate).fetchOne();
    }

    /**
     * Tworzy zapytanie {@link JPAQuery} na podstawie predykatu.
     *
     * @param predicate predykat
     *
     * @return Zapytanie {@link JPAQuery}
     */
    protected JPAQuery<T> createQuery(final Predicate... predicate) {
        JPAQuery<T> query = new JPAQuery<>(em);
        query.from(path).where(predicate);

        return query;
    }

    /**
     * Znajduje encje spełniające predykat.
     *
     * @param predicate predykat
     *
     * @return Encje spełniająca predykat.
     */
    public List<T> findAll(final Predicate predicate) {
        return createQuery(predicate).fetch();
    }

    /**
     * Zlicza encji spełniających predykat.
     *
     * @param predicate predykat
     *
     * @return Ilość encji spełniających predykt.
     */
    public long count(final Predicate predicate) {
        return createQuery(predicate).fetchCount();
    }

    /**
     * Zapisuje encję.
     *
     * @param entity encja
     * @param <S>    typ encji
     *
     * @return Encja
     */
    public <S extends T> S save(final S entity) {
        return jpaRepository.save(entity);
    }

    /**
     * Pobiera wszystkie encje.
     *
     * @return Wszystkie encje.
     */
    public List<T> findAll() {
        return jpaRepository.findAll();
    }

    /**
     * Pobiera wszystkie encje z uwzględnieniem sortowania.
     *
     * @param sort sortowanie
     *
     * @return Posortowane encje
     */
    public List<T> findAll(final Sort sort) {
        return jpaRepository.findAll(sort);
    }

    /**
     * Pobiera encje z uwzględnieniem stronicowania.
     *
     * @param pageable stronicowanie
     *
     * @return Encje należące do danej strony
     */
    public Page<T> findAll(final Pageable pageable) {
        return jpaRepository.findAll(pageable);
    }

    /**
     * Pobiera encję o zadanym id.
     *
     * @param id identyfikator encji
     *
     * @return Encja
     */
    public T findOne(final ID id) {
        return jpaRepository.findOne(id);
    }

    /**
     * Pobiera encje o zadanych id.
     *
     * @param ids identyfikatory encji
     *
     * @return Lista encji
     */
    public List<T> findAll(final Iterable<ID> ids) {
        return jpaRepository.findAll(ids);
    }

    /**
     * Zapisuje listę encji.
     *
     * @param entities encje
     * @param <S>      typ encji
     *
     * @return Lista zapisanych encji
     */
    public <S extends T> List<S> save(final Iterable<S> entities) {
        return jpaRepository.save(entities);
    }

    /**
     * Sprawdza czy encja o przekazanym id istenieje.
     *
     * @param id identyfikator encji
     *
     * @return True - jeżeli encja istnieje, false - w przeciwnym wypadku
     */
    public boolean exists(final ID id) {
        return jpaRepository.exists(id);
    }

    /**
     * Wykonuje zbuforowane operacje na repozytorium.
     */
    public void flush() {
        jpaRepository.flush();
    }

    /**
     * Zapisuje encję i od razu czyści bufor operacji.
     *
     * @param entity encja
     * @param <S>    typ encji
     *
     * @return Zapisana encja
     */
    public <S extends T> S saveAndFlush(final S entity) {
        return jpaRepository.saveAndFlush(entity);
    }

    /**
     * Hurtowane usunięcie encji.
     *
     * @param entities encje do usunięcia
     */
    public void deleteInBatch(final Iterable<T> entities) {
        jpaRepository.deleteInBatch(entities);
    }

    /**
     * Zlicza liczbę encji.
     *
     * @return Liczba encji
     */
    public long count() {
        return jpaRepository.count();
    }

    /**
     * Hurtowo usuwa wszystkie encje z repozytorium.
     */
    public void deleteAllInBatch() {
        jpaRepository.deleteAllInBatch();
    }

    /**
     * Usuwa encję o zadanym id.
     *
     * @param id identyfikator encji
     */
    public void delete(final ID id) {
        jpaRepository.delete(id);
    }

    /**
     * Pobiera encję o zadanym id.
     *
     * @param id identyfikator encji
     *
     * @return encja
     */
    public T getOne(final ID id) {
        return jpaRepository.getOne(id);
    }

    /**
     * Usunięcie encji.
     *
     * @param entity encja do usunięcia
     */
    public void delete(final T entity) {
        jpaRepository.delete(entity);
    }

    /**
     * Usunięcie listy encji.
     *
     * @param entities encje do usunięcia
     */
    public void delete(final Iterable<? extends T> entities) {
        jpaRepository.delete(entities);
    }

    /**
     * Usunięcie wszystkich encji.
     */
    public void deleteAll() {
        jpaRepository.deleteAll();
    }
}