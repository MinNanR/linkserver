<template>
  <div style="margin-left:50px;margin-top:50px;">
    <quill-editor
      v-model="content"
      ref="editor"
      :options="editorOption"
      style="width:50%;height:500px;"
    ></quill-editor>
  </div>
</template>

<script>
import { quillEditor } from "vue-quill-editor";
import "quill/dist/quill.snow.css";
// import * as Quill from 'quill'

export default {
  components: {
    quillEditor,
  },
  data() {
    return {
      editor: null,
      content: `<p></p>`,
      editorOption: {
        theme: "snow",
        placehoder: "请输入正文",
      },
    };
  },
  mounted() {
    this.editor = this.$refs.editor.quill;
  },
};
</script>

<style>
</style>