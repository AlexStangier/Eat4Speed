<template>
  <div class="text-center ma-2">
    <v-snackbar v-model="popupData.display">
      {{ popupData.message }}

      <template v-slot:action="{ attrs }">
        <v-btn
          color="pink"
          text
          v-bind="attrs"
          @click="popupData.display = false"
        >
          Close
        </v-btn>
      </template>
    </v-snackbar>
  </div>
</template>

<script>
export default {
  props: ['popupData'],
};
</script>
