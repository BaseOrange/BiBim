<template>
    <AppPage :show-footer="true">
        <div flex-1>
            <n-card title="区块概况" :segmented="true" mt-15 rounded-10>
                <n-grid x-gap="12" :cols="5">
                    <n-gi span="3">
                        <!-- 区块高度、积累交易数、积累合约数 -->
                        <div flex>
                            <n-card class="w-300 flex-shrink-0 mt-10 mb-10 cursor-pointer" hover:card-shadow title="区块高度">
                                <n-grid x-gap="12" :cols="4">
                                    <n-gi span="1">
                                        <TheIcon icon="icon-park:blockchain" :size="50" />
                                    </n-gi>
                                    <n-gi span="3" class="fontSize">
                                        {{ blockchainInfo.BlockHeight }}
                                    </n-gi>
                                </n-grid>
                            </n-card>
                            <n-card class="w-300 flex-shrink-0 mt-10 mb-10 cursor-pointer" hover:card-shadow title="积累交易数">
                                <n-grid x-gap="12" :cols="4">
                                    <n-gi span="1">
                                        <TheIcon icon="icon-park:coupon" :size="50" />
                                    </n-gi>
                                    <n-gi span="3" class="fontSize">
                                        {{ blockchainInfo.TransactionNum }}
                                    </n-gi>
                                </n-grid>
                            </n-card>
                            <n-card class="w-300 flex-shrink-0 mt-10 mb-10 cursor-pointer" hover:card-shadow title="积累合约数">
                                <n-grid x-gap="12" :cols="4">
                                    <n-gi span="1">
                                        <TheIcon icon="icon-park:bookmark" :size="50" />
                                    </n-gi>
                                    <n-gi span="3" class="fontSize">
                                        {{ blockchainInfo.ContractNum }}
                                    </n-gi>
                                </n-grid>
                            </n-card>
                        </div>
                        <!-- 组织数、节点数、链上用户数 -->
                        <div flex>
                            <n-card class="w-300 flex-shrink-0 mt-10 mb-10 cursor-pointer" hover:card-shadow title="组织数">
                                <n-grid x-gap="12" :cols="4">
                                    <n-gi span="1">
                                        <TheIcon icon="icon-park:building-four" :size="50" />
                                    </n-gi>
                                    <n-gi span="3" class="fontSize">
                                        {{ blockchainInfo.OrgNum }}
                                    </n-gi>
                                </n-grid>
                            </n-card>
                            <n-card class="w-300 flex-shrink-0 mt-10 mb-10 cursor-pointer" hover:card-shadow title="节点数">
                                <n-grid x-gap="12" :cols="4">
                                    <n-gi span="1">
                                        <TheIcon icon="icon-park:two-dimensional-code-one" :size="50" />
                                    </n-gi>
                                    <n-gi span="3" class="fontSize">
                                        {{ blockchainInfo.NodeNum }}
                                    </n-gi>
                                </n-grid>
                            </n-card>
                            <n-card class="w-300 flex-shrink-0 mt-10 mb-10 cursor-pointer" hover:card-shadow title="链上用户数">
                                <n-grid x-gap="12" :cols="4">
                                    <n-gi span="1">
                                        <TheIcon icon="icon-park:every-user" :size="50" />
                                    </n-gi>
                                    <n-gi span="3" class="fontSize">
                                        {{ blockchainInfo.UserNum }}
                                        {{ blockchainInfo.a }}
                                    </n-gi>
                                </n-grid>
                            </n-card>
                        </div>
                    </n-gi>
                    <n-gi span="2">
                        <div id="transVolume" style="width: 100%;height: 100%;"></div>
                    </n-gi>
                </n-grid>
            </n-card>
        </div>
    </AppPage>
</template>
  
<script setup>
import api from '@/api'
import { ref } from 'vue'
import * as echarts from 'echarts'

onMounted(
    () => {
        getBlockchainInfo()
        getTransVolume()
    }
)


// 区块链信息
var blockchainInfo = ref({
})
async function getBlockchainInfo() {
    let { data } = await api.getBlockchainInfo()
    blockchainInfo.value.UserNum = data.Response.Data.UserNum
    blockchainInfo.value.NodeNum = data.Response.Data.NodeNum
    blockchainInfo.value.OrgNum = data.Response.Data.OrgNum
    blockchainInfo.value.ContractNum = data.Response.Data.ContractNum
    blockchainInfo.value.TransactionNum = data.Response.Data.TransactionNum
    blockchainInfo.value.BlockHeight = data.Response.Data.BlockHeight
}

// 24小时交易折线图
var option = {
    title: {
        text: '近24小时上链交易量'
    },
    xAxis: {
        type: 'category',
        boundaryGap: false,
        data: [
            '00', '01', '02', '03', '04', '05', '06', '07',
            '08', '09', '10', '11', '12', '13', '14', '15',
            '16', '17', '18', '19', '20', '21', '22', '23', '24',
        ]
    },
    yAxis: {
        type: 'value'
    },
    series: [
        {
            data: [],
            type: 'line',
            areaStyle: {}
        }
    ]
}
async function getTransVolume() {
    let { data } = await api.getTransVolume()
    for (let i = data.Response.GroupList.length - 1; i >= 0; i--) {
        option.series[0].data.push(data.Response.GroupList[i].TxNum)
    }
    var chartDom = document.getElementById('transVolume')
    var myChart = echarts.init(chartDom)
    myChart.setOption(option)
}

</script>

<style scoped>
.fontSize {
    font-size: 33px;
}
</style>