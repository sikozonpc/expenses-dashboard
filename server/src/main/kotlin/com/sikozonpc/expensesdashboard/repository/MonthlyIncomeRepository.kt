package com.sikozonpc.expensesdashboard.repository

import com.sikozonpc.expensesdashboard.entity.BudgetRule
import com.sikozonpc.expensesdashboard.entity.MonthlyIncome
import org.springframework.data.repository.CrudRepository

interface MonthlyIncomeRepository : CrudRepository<MonthlyIncome, Int>
