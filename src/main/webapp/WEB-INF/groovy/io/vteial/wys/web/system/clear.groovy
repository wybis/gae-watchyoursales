package io.vteial.wys.web.system;

import io.vteial.wys.model.Address
import io.vteial.wys.model.Agency
import io.vteial.wys.model.AutoNumber
import io.vteial.wys.model.Country
import io.vteial.wys.model.Product
import io.vteial.wys.model.Stock
import io.vteial.wys.model.User

println 'clear started...'

try {

	def entities = AutoNumber.findAll()
	entities.each { entity ->
		entity.delete()
	}
	println entities.size() + ' autonNumbers deleted'

	entities = Country.findAll()
	entities.each { entity ->
		entity.delete()
	}
	println entities.size() + ' countrys deleted'

	entities = Address.findAll()
	entities.each { entity ->
		entity.delete()
	}
	println entities.size() + ' addresses deleted'

	entities = User.findAll()
	entities.each { entity ->
		entity.delete()
	}
	println entities.size() + ' users deleted'

	entities = Product.findAll()
	entities.each { entity ->
		entity.delete()
	}
	println entities.size() + ' products deleted'

	entities = Stock.findAll()
	entities.each { entity ->
		entity.delete()
	}
	println entities.size() + ' stocks deleted'

	entities = Agency.findAll()
	entities.each { entity ->
		entity.delete()
	}
	println entities.size() + ' agencys deleted'
}
catch(Throwable t) {
	t.printStackTrace(out)
}

println 'clear finished...'
