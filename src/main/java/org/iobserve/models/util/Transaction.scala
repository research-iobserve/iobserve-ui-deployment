package org.iobserve.models.util

object Transaction {

  val emf = Persistence.createEntityManagerFactory("defaultPersistenceUnit")

  def apply[A](action: EntityManager => A): A = {
    val entityManager = emf.createEntityManager()
    def transaction = entityManager.getTransaction
    transaction.begin()
    try {
      val result = action(entityManager)
      if (transaction.isActive) // Transaktion kann bereits explizit zurückgerollt sein
        transaction.commit()
      result
    }
    catch {
      case exception: Throwable =>
        if (transaction != null && transaction.isActive)
          transaction.rollback()
        throw exception
    }
    finally
      entityManager.close()
  }

  def rollback()(implicit entitymanager: EntityManager) {
    entitymanager.getTransaction().rollback()
  }
}