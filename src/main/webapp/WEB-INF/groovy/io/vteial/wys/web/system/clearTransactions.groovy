package io.vteial.wys.web.system;

import io.vteial.wys.model.Order
import io.vteial.wys.model.OrderReceipt
import io.vteial.wys.model.Tran
import io.vteial.wys.model.TranReceipt

println 'cleaTransactions started...'

try {

	def entities = Order.findAll()
	entities.each { entity ->
		entity.delete()
	}
	println entities.size() + ' orders deleted'

	entities = OrderReceipt.findAll()
	entities.each { entity ->
		entity.delete()
	}
	println entities.size() + ' orderReceipts deleted'

	entities = Tran.findAll()
	entities.each { entity ->
		entity.delete()
	}
	println entities.size() + ' transs deleted'

	entities = TranReceipt.findAll()
	entities.each { entity ->
		entity.delete()
	}
	println entities.size() + ' tranReceipts deleted'
}
catch(Throwable t) {
	t.printStackTrace(out)
}

println 'clearTransactions finished...'