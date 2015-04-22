import io.vteial.wys.model.Product
import io.vteial.wys.model.constants.ProductType


println '''
<html><head><title>Test</title><head><body><pre>
'''
println '-----------------------------------------------------------------'
try {
	def entitys = datastore.execute {
		from Product.class.simpleName
		where branchId == 1
		and type == ProductType.CASH_DEALER
	}

	println("dsize = " + entitys.size())
}
catch(Throwable t) {
	t.printStackTrace(out)
}
println '-----------------------------------------------------------------'

println '''
</pre></body></html>
'''

