<template>
  <div style="margin-left:50px;margin-top:50px;">
    <div v-show="isEditing">
      <div style="margin-bottom:10px">
        <button class="btn btn-primary" style="margin-right:10px" @click="saveIntroduction">保存</button>
        <button class="btn btn-primary" @click="switchEditing()">取消</button>
      </div>

      <div>
        <quill-editor
          v-model="content"
          ref="editor"
          :options="editorOption"
          style="width:50%;height:500px;"
        ></quill-editor>
      </div>
    </div>
    <div v-show="!isEditing">
      <button class="btn btn-primary" style="margin-bottom:10px" @click="switchEditing()">编辑</button>
      <div v-html="introduction"></div>
    </div>

    <div
      class="modal fade"
      id="imageModal"
      tabindex="-1"
      role="dialog"
      aria-labelledby="myModalLabel"
      data-backdrop="static"
    >
      <div class="modal-dialog modal-lg" role="document">
        <div class="modal-content">
          <div class="modal-header">
            <button type="button" class="close" data-dismiss="modal" aria-label="Close">
              <span aria-hidden="true">&times;</span>
            </button>
            <h4 class="modal-title" id="imageModalLabel">选择图片</h4>
          </div>
          <div class="modal-body">
            <form>
              <div class="form-group">
                <label for="file">图片</label>
                <input type="file" id="file" ref="upload" @change="uploadImage()" />
                <p class="help-block">请选择要添加的图片</p>
              </div>
            </form>
            <div class="container-fliud">
              <div
                v-for="(item,index) in imageList"
                :key="index"
                class="col-md-4"
                style="width:200px;height:250px;margin-top:30px"
              >
                <div style="height:200px;">
                  <img style="width:100%;hegith:auto" :src="item.url" class="img-thumbnail" />
                </div>
                <div style="width:100%;text-align:center">
                  <button class="btn btn-primary" style="width:30%;" @click="selectImage(index)">选择</button>
                  <button
                    class="btn btn-danger"
                    style="width:30%;margin-left:20px;"
                    @click="deleteImage(item.id)"
                  >删除</button>
                </div>
              </div>
            </div>
          </div>
          <div class="modal-footer"></div>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import { quillEditor } from "vue-quill-editor";
import "quill/dist/quill.snow.css";
import axios from "axios";
import $ from "jquery";
// import * as Quill from 'quill'

const toolbarOptions = [
  ["bold", "italic", "underline", "strike"], // toggled buttons
  ["blockquote", "code-block"],

  [{ header: 1 }, { header: 2 }], // custom button values
  [{ list: "ordered" }, { list: "bullet" }],
  [{ script: "sub" }, { script: "super" }], // superscript/subscript
  [{ indent: "-1" }, { indent: "+1" }], // outdent/indent
  [{ direction: "rtl" }], // text direction

  [{ size: ["small", false, "large", "huge"] }], // custom dropdown
  [{ header: [1, 2, 3, 4, 5, 6, false] }],

  [{ color: [] }, { background: [] }], // dropdown with defaults from theme
  [{ font: [] }],
  [{ align: [] }],
  ["link", "image", "video"],
  ["clean"], // remove formatting button
];

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
        modules: {
          toolbar: {
            container: toolbarOptions,
            handlers: {
              image: this.openSelectImageModal,
            },
          },
        },
      },
      file: null,
      isEditing: false,
      introduction: "",
      imageList: [],
    };
  },
  methods: {
    getIntroduction() {
      this.request
        .post("api/getIntroduction")
        .then((response) => {
          this.introduction = response.data.introduction;
        })
        .catch((error) => {
          console.log(error);
        });
    },
    saveIntroduction() {
      this.request
        .post("manager/updateIntroduction", { content: this.content })
        .then((response) => {
          alert(response.message);
          this.getIntroduction();
          this.switchEditing();
        })
        .catch((error) => {
          console.log(error);
        });
    },
    switchEditing() {
      this.isEditing = !this.isEditing;
      this.content = this.introduction;
    },
    openSelectImageModal(value) {
      if (value) {
        // let uploadInput = this.$refs.upload;
        // uploadInput.click();
        $("#imageModal").modal("show");
      }
    },
    async uploadImage() {
      let baseUrl = this.baseUrl;
      let formData = new FormData();
      let image = this.$refs.upload.files[0];
      console.log(baseUrl);
      formData.append("image", image);
      await axios({
        url: `${baseUrl}/manager/addImage`,
        data: formData,
        method: "post",
        headers: {
          "Content-Type": "multipart/form-data",
          authorization: localStorage.getItem("token"),
        },
      })
        .then(() => {
          this.getImageList();
        })
        .catch((error) => {
          console.log(error);
        });
      this.formData = null;
    },
    getImageList() {
      this.request
        .post("/manager/getImageList")
        .then((response) => {
          this.imageList = response.data;
        })
        .catch((error) => {
          console.log(error);
        });
    },
    selectImage(index) {
      let editor = this.$refs.editor.quill;
      let quillIndex = editor.selection.savedRange.index;
      editor.insertEmbed(quillIndex, "image", this.imageList[index].url);
      editor.setSelection(quillIndex + 1);
      $("#imageModal").modal("hide");
    },
    deleteImage(id) {
      this.request
        .post("/manager/deleteImage", {id: id})
        .then(() => {
          this.getImageList();
        })
        .catch((error) => {
          console.log(error);
        });
    },
  },
  mounted() {
    this.editor = this.$refs.editor.quill;
    this.getIntroduction();
    this.getImageList();
  },
};
</script>

<style>
</style>