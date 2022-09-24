package com.sikozonpc.expensesdashboard.repository

import com.sikozonpc.expensesdashboard.entity.BudgetRule
import org.springframework.data.repository.CrudRepository

interface BudgetRuleRepository : CrudRepository<BudgetRule, Int>
