<template>
	<a-layout>
		<a-layout-header
			:style="{
				position: 'fixed',
				zIndex: 1,
				width: '100%',
				background: '#fff',
        padding: 0,
				height: '80px'
			}"
		>
			<AppHead/>
		</a-layout-header>

		<a-layout-content :style="{  marginTop: '80px', minWidth: '100%' }">
			<div class="container">
				<router-view></router-view>
			</div>
		</a-layout-content>
	</a-layout>
</template>

<script setup>
	import AppHead from '@/components/AppHead.vue'
</script>

<style lang="less" scoped>
	.container {
		min-width: calc(100vw - 40rem);
		min-height: calc(100vh - 80px);
	}

</style>
