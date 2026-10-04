package springbootai.budgeting.infrastructure.persistence;

import springbootai.budgeting.domain.Category;
import springbootai.budgeting.domain.Transaction;
import springbootai.budgeting.domain.TransactionRepository;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class TransactionRepositoryAdapter implements TransactionRepository {
    private final TransactionJpaRepository transactionJpaRepository;

    public TransactionRepositoryAdapter(TransactionJpaRepository transactionJpaRepository) {
        this.transactionJpaRepository = transactionJpaRepository;
    }

    @Override
    public Transaction save(Transaction transaction) {
        var entity = TransactionEntity.from(transaction);
        var savedEntity = transactionJpaRepository.save(entity);
        return savedEntity.toDomain();
    }

    @Override
    public List<Transaction> findAllByCategory(Category category) {
        return transactionJpaRepository.findByCategory(category)
                .stream()
                .map(TransactionEntity::toDomain)
                .toList();
    }
}
