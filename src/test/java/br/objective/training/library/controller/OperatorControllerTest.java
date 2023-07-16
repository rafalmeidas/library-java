package br.objective.training.library.controller;

import br.objective.training.library.dto.request.OperatorRequestDto;
import br.objective.training.library.entities.Operator;
import br.objective.training.library.repository.OperatorRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.skyscreamer.jsonassert.JSONAssert;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;

class OperatorControllerTest extends AbstractControllerTest {
    @MockBean
    private OperatorRepository repository;
    private OperatorRequestDto requestDto;

    @BeforeEach
    public void setup() {
        this.requestDto = this.createReqDto();
    }

    @Test
    @DisplayName("PUT /api/operators: Esperado que ao receber um dto inválido, com nome nulo, retorne uma exceção")
    public void givenOperatorsWhenCreateWithNameNullThenExpects400() throws Exception {
        when(repository.findByEmail(eq(this.requestDto.getEmail()))).thenReturn(Optional.empty());
        requestDto.setName(null);
        mockMvc.perform(put("/api/operators")
                        .accept(MediaType.APPLICATION_JSON_VALUE)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(serializeInput(requestDto)))
                .andDo(result -> {
                    responseStatus(result, HttpStatus.BAD_REQUEST);
                    JSONAssert.assertEquals("""
                                    {
                                        "errors": [
                                            {
                                                "fieldName": "name",
                                                "message": "não deve estar em branco"
                                            }
                                        ]
                                    }
                                    """,
                            getContentAsString(result), true);
                });
    }

    @Test
    @DisplayName("PUT /api/operators: Esperado que ao receber um dto inválido, com nome menor que 4, retorne uma exceção")
    public void givenOperatorsWhenCreateWithNameLessThan4ThenExpects400() throws Exception {
        when(repository.findByEmail(eq(this.requestDto.getEmail()))).thenReturn(Optional.empty());
        requestDto.setName("na");
        mockMvc.perform(put("/api/operators")
                        .accept(MediaType.APPLICATION_JSON_VALUE)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(serializeInput(requestDto)))
                .andDo(result -> {
                    responseStatus(result, HttpStatus.BAD_REQUEST);
                    JSONAssert.assertEquals("""
                                    {
                                        "errors": [
                                            {
                                                "fieldName": "name",
                                                "message": "tamanho deve ser entre 4 e 250"
                                            }
                                        ]
                                    }
                                    """,
                            getContentAsString(result), true);
                });
    }

    @Test
    @DisplayName("PUT /api/operators: Esperado que ao receber um dto inválido, com nome maior que 250, retorne uma exceção")
    public void givenOperatorsWhenCreateWithNameGreaterThan250ThenExpects400() throws Exception {
        when(repository.findByEmail(eq(this.requestDto.getEmail()))).thenReturn(Optional.empty());
        requestDto.setName("Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Sit amet nisl purus in mollis nunc. Elit duis tristique sollicitudin nibh sit amet commodo. Molestie a iaculis at erat pellentesque adipiscing commodo. Lectus quam id leo in vitae turpis massa sed. Facilisis leo vel fringilla est ullamcorper eget. Donec ac odio tempor orci dapibus ultrices. Aliquam nulla facilisi cras fermentum odio eu feugiat. Quis vel eros donec ac odio tempor orci dapibus ultrices. Ut eu sem integer vitae justo eget magna fermentum iaculis. Condimentum id venenatis a condimentum vitae sapien. Porta lorem mollis aliquam ut porttitor leo. Viverra ipsum nunc aliquet bibendum enim facilisis gravida neque convallis. Consequat ac felis donec et odio. Mattis nunc sed blandit libero volutpat sed. Venenatis a condimentum vitae sapien pellentesque habitant morbi tristique. Id semper risus in hendrerit gravida rutrum quisque non tellus. Ornare aenean euismod elementum nisi quis eleifend quam. Turpis egestas integer eget aliquet nibh praesent. Massa tincidunt dui ut ornare. Feugiat in ante metus dictum at. Amet facilisis magna etiam tempor. Vel orci porta non pulvinar neque laoreet suspendisse interdum consectetur. Tortor aliquam nulla facilisi cras fermentum odio. Sapien faucibus et molestie ac feugiat sed. Sed velit dignissim sodales ut eu sem. Nunc lobortis mattis aliquam faucibus purus in. Quam elementum pulvinar etiam non quam lacus. Vel risus commodo viverra maecenas accumsan. Ornare arcu odio ut sem nulla. Nisl nunc mi ipsum faucibus vitae aliquet nec ullamcorper sit. Rutrum quisque non tellus orci ac auctor augue. Viverra tellus in hac habitasse platea. Amet aliquam id diam maecenas ultricies mi eget mauris pharetra. Ultricies integer quis auctor elit sed vulputate. Faucibus purus in massa tempor nec feugiat nisl pretium. Sodales neque sodales ut etiam sit amet nisl purus in. Consequat interdum varius sit amet. Non nisi est sit amet. Facilisi cras fermentum odio eu feugiat pretium nibh. Eu nisl nunc mi ipsum faucibus vitae aliquet nec. Sed arcu non odio euismod lacinia. Nulla facilisi morbi tempus iaculis urna id. Elementum sagittis vitae et leo duis. Semper risus in hendrerit gravida rutrum quisque. Quam viverra orci sagittis eu volutpat odio facilisis mauris. Cursus euismod quis viverra nibh cras pulvinar mattis. Est velit egestas dui id ornare arcu odio. Nec feugiat nisl pretium fusce id velit ut tortor. Ut consequat semper viverra nam libero justo. Morbi blandit cursus risus at ultrices mi tempus. Volutpat consequat mauris nunc congue nisi vitae suscipit tellus. Enim neque volutpat ac tincidunt vitae semper quis lectus. Elementum tempus egestas sed sed risus pretium. Eleifend mi in nulla posuere sollicitudin aliquam ultrices sagittis. Mattis vulputate enim nulla aliquet porttitor lacus luctus. Nisi vitae suscipit tellus mauris a diam. Donec ultrices tincidunt arcu non sodales neque sodales. Lorem sed risus ultricies tristique nulla aliquet enim. Gravida quis blandit turpis cursus. Enim ut tellus elementum sagittis vitae et. Urna nec tincidunt praesent semper feugiat nibh sed pulvinar proin. Felis bibendum ut tristique et. Sed blandit libero volutpat sed cras. Tempor nec feugiat nisl pretium fusce id velit ut. Commodo nulla facilisi nullam vehicula ipsum a. Sit amet facilisis magna etiam. Pellentesque eu tincidunt tortor aliquam nulla facilisi. Nec dui nunc mattis enim ut tellus. Dis parturient montes nascetur ridiculus. Non odio euismod lacinia at quis risus sed vulputate odio. Pharetra convallis posuere morbi leo urna. Sed cras ornare arcu dui vivamus arcu felis bibendum. Dignissim convallis aenean et tortor at. Id diam maecenas ultricies mi. Interdum posuere lorem ipsum dolor sit amet consectetur adipiscing elit. Cursus vitae congue mauris rhoncus. Mauris nunc congue nisi vitae. Augue neque gravida in fermentum et sollicitudin ac orci. In egestas erat imperdiet sed euismod nisi porta lorem mollis. Mauris pharetra et ultrices neque. Lobortis feugiat vivamus at augue eget arcu dictum varius. Ac orci phasellus egestas tellus rutrum tellus pellentesque. Consectetur adipiscing elit pellentesque habitant morbi tristique. Non odio euismod lacinia at. Senectus et netus et malesuada fames. Nec ullamcorper sit amet risus nullam eget felis. Elementum facilisis leo vel fringilla est ullamcorper eget nulla. Non pulvinar neque laoreet suspendisse interdum consectetur libero id. Mattis pellentesque id nibh tortor id aliquet lectus. Faucibus vitae aliquet nec ullamcorper sit amet risus. Mauris nunc congue nisi vitae suscipit tellus. Gravida neque convallis a cras semper auctor neque. Eu ultrices vitae auctor eu augue ut lectus arcu bibendum. Non pulvinar neque laoreet suspendisse interdum. Quis auctor elit sed vulputate mi sit. Eu feugiat pretium nibh ipsum consequat. Malesuada pellentesque elit eget gravida cum sociis natoque penatibus et. Volutpat sed cras ornare arcu dui. Egestas sed tempus urna et pharetra pharetra massa. Vel quam elementum pulvinar etiam non quam lacus. Venenatis lectus magna fringilla urna porttitor rhoncus. Cursus turpis massa tincidunt dui. In eu mi bibendum neque egestas congue quisque egestas. Turpis egestas pretium aenean pharetra magna. Nunc pulvinar sapien et ligula ullamcorper malesuada. Elementum sagittis vitae et leo duis ut diam quam. Tincidunt lobortis feugiat vivamus at augue. Vitae sapien pellentesque habitant morbi tristique senectus et netus. Adipiscing tristique risus nec feugiat. Neque convallis a cras semper auctor neque vitae tempus. Integer quis auctor elit sed vulputate mi sit amet. Vitae proin sagittis nisl rhoncus. Lectus quam id leo in vitae turpis massa sed elementum. Interdum varius sit amet mattis vulputate enim. Vel orci porta non pulvinar neque laoreet suspendisse interdum. Nisl nisi scelerisque eu ultrices vitae auctor eu. Urna id volutpat lacus laoreet non curabitur gravida. Velit aliquet sagittis id consectetur purus. Neque laoreet suspendisse interdum consectetur libero id. Sagittis orci a scelerisque purus. Scelerisque in dictum non consectetur a erat nam at. Sit amet massa vitae tortor condimentum. Nunc scelerisque viverra mauris in. Aliquam sem et tortor consequat id porta nibh. Sem viverra aliquet eget sit amet tellus. Viverra nibh cras pulvinar mattis nunc sed. Etiam non quam lacus suspendisse faucibus interdum posuere. Gravida neque convallis a cras. Natoque penatibus et magnis dis parturient montes nascetur ridiculus mus. Dolor magna eget est lorem. Aenean sed adipiscing diam donec adipiscing. Orci ac auctor augue mauris augue neque gravida in. In hendrerit gravida rutrum quisque non tellus orci ac auctor. Ultricies leo integer malesuada nunc vel risus. Faucibus a pellentesque sit amet. Diam quam nulla porttitor massa id neque aliquam vestibulum morbi. Sem et tortor consequat id. Tincidunt nunc pulvinar sapien et ligula. Laoreet id donec ultrices tincidunt arcu non. Quam vulputate dignissim suspendisse in est ante. Semper viverra nam libero justo. Libero id faucibus nisl tincidunt. Nunc congue nisi vitae suscipit tellus mauris a. Velit dignissim sodales ut eu sem. Habitant morbi tristique senectus et netus. Proin sagittis nisl rhoncus mattis rhoncus. Massa enim nec dui nunc mattis enim ut. Dignissim convallis aenean et tortor at risus viverra adipiscing. Aliquam eleifend mi in nulla posuere sollicitudin aliquam. Interdum varius sit amet mattis. Nibh cras pulvinar mattis nunc sed blandit libero. Eleifend mi in nulla posuere sollicitudin aliquam ultrices sagittis orci. Augue eget arcu dictum varius duis. In aliquam sem fringilla ut morbi tincidunt augue. Mi quis hendrerit dolor magna eget est lorem. Tempus urna et pharetra pharetra massa massa ultricies mi. At augue eget arcu dictum varius duis at consectetur. Tortor condimentum lacinia quis vel eros donec. Consequat nisl vel pretium lectus. Vivamus at augue eget arcu dictum varius. Eget mi proin sed libero. Et malesuada fames ac turpis egestas integer eget aliquet. Nisi est sit amet facilisis magna etiam tempor. Nisl pretium fusce id velit ut tortor pretium viverra suspendisse. Turpis nunc eget lorem dolor sed viverra. Lacus sed viverra tellus in hac habitasse platea dictumst vestibulum. Scelerisque mauris pellentesque pulvinar pellentesque habitant morbi tristique. Lorem ipsum dolor sit amet consectetur adipiscing. Ipsum dolor sit amet consectetur adipiscing elit. Et pharetra pharetra massa massa ultricies mi quis. Nibh nisl condimentum id venenatis a condimentum vitae sapien. Aliquet enim tortor at auctor urna nunc id cursus. Suscipit tellus mauris a diam maecenas sed enim. Amet mauris commodo quis imperdiet massa tincidunt. Varius vel pharetra vel turpis nunc. Egestas sed sed risus pretium quam vulputate dignissim suspendisse in. Faucibus in ornare quam viverra orci sagittis eu volutpat odio. Adipiscing elit pellentesque habitant morbi tristique senectus et netus. Diam donec adipiscing tristique risus nec feugiat in fermentum. Ac turpis egestas maecenas pharetra convallis. Pulvinar pellentesque habitant morbi tristique. Sagittis purus sit amet volutpat consequat mauris nunc. Id consectetur purus ut faucibus pulvinar elementum integer. Suspendisse sed nisi lacus sed viverra. Nunc vel risus commodo viverra. Dignissim sodales ut eu sem integer vitae justo. Egestas integer eget aliquet nibh praesent tristique magna. Quis eleifend quam adipiscing vitae proin sagittis nisl. Sed faucibus turpis in eu mi bibendum. Faucibus nisl tincidunt eget nullam non nisi est. Faucibus purus in massa tempor nec feugiat nisl pretium. Etiam tempor orci eu lobortis elementum nibh tellus. Gravida neque convallis a cras semper auctor neque vitae. Augue mauris augue neque gravida in fermentum. Praesent elementum facilisis leo vel fringilla est. Adipiscing bibendum est ultricies integer. Ut eu sem integer vitae justo eget magna fermentum. Vulputate ut pharetra sit amet aliquam id diam maecenas ultricies. Adipiscing vitae proin sagittis nisl rhoncus mattis rhoncus urna. Nibh venenatis cras sed felis eget velit aliquet. Sed viverra ipsum nunc aliquet bibendum enim facilisis gravida. Purus semper eget duis at. Morbi non arcu risus quis varius quam quisque id diam. Ipsum faucibus vitae aliquet nec ullamcorper sit. Et ligula ullamcorper malesuada proin libero nunc consequat interdum varius. Sapien et ligula ullamcorper malesuada proin libero nunc. Massa sed elementum tempus egestas sed sed risus pretium. Sapien et ligula ullamcorper malesuada proin libero nunc consequat. Ligula ullamcorper malesuada proin libero nunc consequat interdum. Non enim praesent elementum facilisis leo vel fringilla est. Nec dui nunc mattis enim ut tellus. Eget est lorem ipsum dolor sit amet consectetur adipiscing elit. Ipsum nunc aliquet bibendum enim facilisis gravida. Pellentesque elit ullamcorper dignissim cras tincidunt lobortis feugiat vivamus at. Aenean pharetra magna ac placerat vestibulum lectus mauris. Phasellus vestibulum lorem sed risus ultricies tristique nulla aliquet enim. Fringilla phasellus faucibus scelerisque eleifend donec. Odio morbi quis commodo odio aenean sed adipiscing. Tortor dignissim convallis aenean et tortor at risus. Lacus luctus accumsan tortor posuere ac ut. Nec tincidunt praesent semper feugiat nibh sed pulvinar proin. Aliquet sagittis id consectetur purus ut faucibus. Pharetra convallis posuere morbi leo urna. Turpis in eu mi bibendum neque. Purus viverra accumsan in nisl. Tincidunt tortor aliquam nulla facilisi. Nunc non blandit massa enim nec dui nunc mattis. Vel facilisis volutpat est velit egestas. Dictum varius duis at consectetur lorem donec massa sapien. Pellentesque diam volutpat commodo sed egestas egestas fringilla. Tortor posuere ac ut consequat semper. Enim blandit volutpat maecenas volutpat blandit aliquam etiam. Lectus mauris ultrices eros in cursus turpis massa tincidunt dui. Massa vitae tortor condimentum lacinia. In est ante in nibh. Nisl purus in mollis nunc sed. Sit amet risus nullam eget felis eget nunc lobortis mattis. Aliquet lectus proin nibh nisl condimentum id. Amet consectetur adipiscing elit ut aliquam purus. Mi proin sed libero enim sed faucibus turpis. Imperdiet dui accumsan sit amet nulla facilisi morbi tempus iaculis. Non nisi est sit amet facilisis magna. Vitae congue mauris rhoncus aenean vel elit scelerisque mauris pellentesque. Sit amet tellus cras adipiscing. Ultrices mi tempus imperdiet nulla malesuada pellentesque. Felis eget velit aliquet sagittis id consectetur purus. Id nibh tortor id aliquet lectus proin nibh. Lobortis feugiat vivamus at augue eget arcu. A pellentesque sit amet porttitor eget dolor. In iaculis nunc sed augue lacus viverra vitae.");
        mockMvc.perform(put("/api/operators")
                        .accept(MediaType.APPLICATION_JSON_VALUE)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(serializeInput(requestDto)))
                .andDo(result -> {
                    responseStatus(result, HttpStatus.BAD_REQUEST);
                    JSONAssert.assertEquals("""
                                    {
                                        "errors": [
                                            {
                                                "fieldName": "name",
                                                "message": "tamanho deve ser entre 4 e 250"
                                            }
                                        ]
                                    }
                                    """,
                            getContentAsString(result), true);
                });
    }

    @Test
    @DisplayName("PUT /api/operators: Esperado que ao receber um dto inválido, com e-mail nulo, retorne uma exceção")
    public void givenOperatorsWhenCreateWithEmailNullThenExpects400() throws Exception {
        when(repository.findByEmail(eq(this.requestDto.getEmail()))).thenReturn(Optional.empty());
        requestDto.setEmail(null);
        mockMvc.perform(put("/api/operators")
                        .accept(MediaType.APPLICATION_JSON_VALUE)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(serializeInput(requestDto)))
                .andDo(result -> {
                    responseStatus(result, HttpStatus.BAD_REQUEST);
                    JSONAssert.assertEquals("""
                                    {
                                        "errors": [
                                            {
                                                "fieldName": "email",
                                                "message": "não deve estar em branco"
                                            }
                                        ]
                                    }
                                    """,
                            getContentAsString(result), true);
                });
    }

    @Test
    @DisplayName("PUT /api/operators: Esperado que ao receber um dto inválido, com e-mail mal formatado, retorne uma exceção")
    public void givenOperatorsWhenCreateWithEmailLessThan4ThenExpects400() throws Exception {
        when(repository.findByEmail(eq(this.requestDto.getEmail()))).thenReturn(Optional.empty());
        requestDto.setEmail("na");
        mockMvc.perform(put("/api/operators")
                        .accept(MediaType.APPLICATION_JSON_VALUE)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(serializeInput(requestDto)))
                .andDo(result -> {
                    responseStatus(result, HttpStatus.BAD_REQUEST);
                    JSONAssert.assertEquals("""
                                    {
                                        "errors": [
                                            {
                                                "fieldName": "email",
                                                "message": "deve ser um endereço de e-mail bem formado"
                                            }
                                        ]
                                    }
                                    """,
                            getContentAsString(result), true);
                });
    }

    @Test
    @DisplayName("PUT /api/operators: Esperado que ao receber um dto inválido, com senha nula, retorne uma exceção")
    public void givenOperatorsWhenCreateWithPasswordNullThenExpects400() throws Exception {
        when(repository.findByEmail(eq(this.requestDto.getEmail()))).thenReturn(Optional.empty());
        requestDto.setPassword(null);
        mockMvc.perform(put("/api/operators")
                        .accept(MediaType.APPLICATION_JSON_VALUE)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(serializeInput(requestDto)))
                .andDo(result -> {
                    responseStatus(result, HttpStatus.BAD_REQUEST);
                    JSONAssert.assertEquals("""
                                    {
                                        "errors": [
                                            {
                                                "fieldName": "password",
                                                "message": "não deve estar em branco"
                                            }
                                        ]
                                    }
                                    """,
                            getContentAsString(result), true);
                });
    }

    @Test
    @DisplayName("PUT /api/operators: Esperado que ao receber um dto inválido, com senha menor que 4, retorne uma exceção")
    public void givenOperatorsWhenCreateWithPasswordLessThan4ThenExpects400() throws Exception {
        when(repository.findByEmail(eq(this.requestDto.getEmail()))).thenReturn(Optional.empty());
        requestDto.setPassword("na");
        mockMvc.perform(put("/api/operators")
                        .accept(MediaType.APPLICATION_JSON_VALUE)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(serializeInput(requestDto)))
                .andDo(result -> {
                    responseStatus(result, HttpStatus.BAD_REQUEST);
                    JSONAssert.assertEquals("""
                                    {
                                        "errors": [
                                            {
                                                "fieldName": "password",
                                                "message": "tamanho deve ser entre 4 e 250"
                                            }
                                        ]
                                    }
                                    """,
                            getContentAsString(result), true);
                });
    }

    @Test
    @DisplayName("PUT /api/operators: Esperado que ao receber um dto inválido, com senha maior que 250, retorne uma exceção")
    public void givenOperatorsWhenCreateWithPasswordGreaterThan250ThenExpects400() throws Exception {
        when(repository.findByEmail(eq(this.requestDto.getEmail()))).thenReturn(Optional.empty());
        requestDto.setPassword("Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Sit amet nisl purus in mollis nunc. Elit duis tristique sollicitudin nibh sit amet commodo. Molestie a iaculis at erat pellentesque adipiscing commodo. Lectus quam id leo in vitae turpis massa sed. Facilisis leo vel fringilla est ullamcorper eget. Donec ac odio tempor orci dapibus ultrices. Aliquam nulla facilisi cras fermentum odio eu feugiat. Quis vel eros donec ac odio tempor orci dapibus ultrices. Ut eu sem integer vitae justo eget magna fermentum iaculis. Condimentum id venenatis a condimentum vitae sapien. Porta lorem mollis aliquam ut porttitor leo. Viverra ipsum nunc aliquet bibendum enim facilisis gravida neque convallis. Consequat ac felis donec et odio. Mattis nunc sed blandit libero volutpat sed. Venenatis a condimentum vitae sapien pellentesque habitant morbi tristique. Id semper risus in hendrerit gravida rutrum quisque non tellus. Ornare aenean euismod elementum nisi quis eleifend quam. Turpis egestas integer eget aliquet nibh praesent. Massa tincidunt dui ut ornare. Feugiat in ante metus dictum at. Amet facilisis magna etiam tempor. Vel orci porta non pulvinar neque laoreet suspendisse interdum consectetur. Tortor aliquam nulla facilisi cras fermentum odio. Sapien faucibus et molestie ac feugiat sed. Sed velit dignissim sodales ut eu sem. Nunc lobortis mattis aliquam faucibus purus in. Quam elementum pulvinar etiam non quam lacus. Vel risus commodo viverra maecenas accumsan. Ornare arcu odio ut sem nulla. Nisl nunc mi ipsum faucibus vitae aliquet nec ullamcorper sit. Rutrum quisque non tellus orci ac auctor augue. Viverra tellus in hac habitasse platea. Amet aliquam id diam maecenas ultricies mi eget mauris pharetra. Ultricies integer quis auctor elit sed vulputate. Faucibus purus in massa tempor nec feugiat nisl pretium. Sodales neque sodales ut etiam sit amet nisl purus in. Consequat interdum varius sit amet. Non nisi est sit amet. Facilisi cras fermentum odio eu feugiat pretium nibh. Eu nisl nunc mi ipsum faucibus vitae aliquet nec. Sed arcu non odio euismod lacinia. Nulla facilisi morbi tempus iaculis urna id. Elementum sagittis vitae et leo duis. Semper risus in hendrerit gravida rutrum quisque. Quam viverra orci sagittis eu volutpat odio facilisis mauris. Cursus euismod quis viverra nibh cras pulvinar mattis. Est velit egestas dui id ornare arcu odio. Nec feugiat nisl pretium fusce id velit ut tortor. Ut consequat semper viverra nam libero justo. Morbi blandit cursus risus at ultrices mi tempus. Volutpat consequat mauris nunc congue nisi vitae suscipit tellus. Enim neque volutpat ac tincidunt vitae semper quis lectus. Elementum tempus egestas sed sed risus pretium. Eleifend mi in nulla posuere sollicitudin aliquam ultrices sagittis. Mattis vulputate enim nulla aliquet porttitor lacus luctus. Nisi vitae suscipit tellus mauris a diam. Donec ultrices tincidunt arcu non sodales neque sodales. Lorem sed risus ultricies tristique nulla aliquet enim. Gravida quis blandit turpis cursus. Enim ut tellus elementum sagittis vitae et. Urna nec tincidunt praesent semper feugiat nibh sed pulvinar proin. Felis bibendum ut tristique et. Sed blandit libero volutpat sed cras. Tempor nec feugiat nisl pretium fusce id velit ut. Commodo nulla facilisi nullam vehicula ipsum a. Sit amet facilisis magna etiam. Pellentesque eu tincidunt tortor aliquam nulla facilisi. Nec dui nunc mattis enim ut tellus. Dis parturient montes nascetur ridiculus. Non odio euismod lacinia at quis risus sed vulputate odio. Pharetra convallis posuere morbi leo urna. Sed cras ornare arcu dui vivamus arcu felis bibendum. Dignissim convallis aenean et tortor at. Id diam maecenas ultricies mi. Interdum posuere lorem ipsum dolor sit amet consectetur adipiscing elit. Cursus vitae congue mauris rhoncus. Mauris nunc congue nisi vitae. Augue neque gravida in fermentum et sollicitudin ac orci. In egestas erat imperdiet sed euismod nisi porta lorem mollis. Mauris pharetra et ultrices neque. Lobortis feugiat vivamus at augue eget arcu dictum varius. Ac orci phasellus egestas tellus rutrum tellus pellentesque. Consectetur adipiscing elit pellentesque habitant morbi tristique. Non odio euismod lacinia at. Senectus et netus et malesuada fames. Nec ullamcorper sit amet risus nullam eget felis. Elementum facilisis leo vel fringilla est ullamcorper eget nulla. Non pulvinar neque laoreet suspendisse interdum consectetur libero id. Mattis pellentesque id nibh tortor id aliquet lectus. Faucibus vitae aliquet nec ullamcorper sit amet risus. Mauris nunc congue nisi vitae suscipit tellus. Gravida neque convallis a cras semper auctor neque. Eu ultrices vitae auctor eu augue ut lectus arcu bibendum. Non pulvinar neque laoreet suspendisse interdum. Quis auctor elit sed vulputate mi sit. Eu feugiat pretium nibh ipsum consequat. Malesuada pellentesque elit eget gravida cum sociis natoque penatibus et. Volutpat sed cras ornare arcu dui. Egestas sed tempus urna et pharetra pharetra massa. Vel quam elementum pulvinar etiam non quam lacus. Venenatis lectus magna fringilla urna porttitor rhoncus. Cursus turpis massa tincidunt dui. In eu mi bibendum neque egestas congue quisque egestas. Turpis egestas pretium aenean pharetra magna. Nunc pulvinar sapien et ligula ullamcorper malesuada. Elementum sagittis vitae et leo duis ut diam quam. Tincidunt lobortis feugiat vivamus at augue. Vitae sapien pellentesque habitant morbi tristique senectus et netus. Adipiscing tristique risus nec feugiat. Neque convallis a cras semper auctor neque vitae tempus. Integer quis auctor elit sed vulputate mi sit amet. Vitae proin sagittis nisl rhoncus. Lectus quam id leo in vitae turpis massa sed elementum. Interdum varius sit amet mattis vulputate enim. Vel orci porta non pulvinar neque laoreet suspendisse interdum. Nisl nisi scelerisque eu ultrices vitae auctor eu. Urna id volutpat lacus laoreet non curabitur gravida. Velit aliquet sagittis id consectetur purus. Neque laoreet suspendisse interdum consectetur libero id. Sagittis orci a scelerisque purus. Scelerisque in dictum non consectetur a erat nam at. Sit amet massa vitae tortor condimentum. Nunc scelerisque viverra mauris in. Aliquam sem et tortor consequat id porta nibh. Sem viverra aliquet eget sit amet tellus. Viverra nibh cras pulvinar mattis nunc sed. Etiam non quam lacus suspendisse faucibus interdum posuere. Gravida neque convallis a cras. Natoque penatibus et magnis dis parturient montes nascetur ridiculus mus. Dolor magna eget est lorem. Aenean sed adipiscing diam donec adipiscing. Orci ac auctor augue mauris augue neque gravida in. In hendrerit gravida rutrum quisque non tellus orci ac auctor. Ultricies leo integer malesuada nunc vel risus. Faucibus a pellentesque sit amet. Diam quam nulla porttitor massa id neque aliquam vestibulum morbi. Sem et tortor consequat id. Tincidunt nunc pulvinar sapien et ligula. Laoreet id donec ultrices tincidunt arcu non. Quam vulputate dignissim suspendisse in est ante. Semper viverra nam libero justo. Libero id faucibus nisl tincidunt. Nunc congue nisi vitae suscipit tellus mauris a. Velit dignissim sodales ut eu sem. Habitant morbi tristique senectus et netus. Proin sagittis nisl rhoncus mattis rhoncus. Massa enim nec dui nunc mattis enim ut. Dignissim convallis aenean et tortor at risus viverra adipiscing. Aliquam eleifend mi in nulla posuere sollicitudin aliquam. Interdum varius sit amet mattis. Nibh cras pulvinar mattis nunc sed blandit libero. Eleifend mi in nulla posuere sollicitudin aliquam ultrices sagittis orci. Augue eget arcu dictum varius duis. In aliquam sem fringilla ut morbi tincidunt augue. Mi quis hendrerit dolor magna eget est lorem. Tempus urna et pharetra pharetra massa massa ultricies mi. At augue eget arcu dictum varius duis at consectetur. Tortor condimentum lacinia quis vel eros donec. Consequat nisl vel pretium lectus. Vivamus at augue eget arcu dictum varius. Eget mi proin sed libero. Et malesuada fames ac turpis egestas integer eget aliquet. Nisi est sit amet facilisis magna etiam tempor. Nisl pretium fusce id velit ut tortor pretium viverra suspendisse. Turpis nunc eget lorem dolor sed viverra. Lacus sed viverra tellus in hac habitasse platea dictumst vestibulum. Scelerisque mauris pellentesque pulvinar pellentesque habitant morbi tristique. Lorem ipsum dolor sit amet consectetur adipiscing. Ipsum dolor sit amet consectetur adipiscing elit. Et pharetra pharetra massa massa ultricies mi quis. Nibh nisl condimentum id venenatis a condimentum vitae sapien. Aliquet enim tortor at auctor urna nunc id cursus. Suscipit tellus mauris a diam maecenas sed enim. Amet mauris commodo quis imperdiet massa tincidunt. Varius vel pharetra vel turpis nunc. Egestas sed sed risus pretium quam vulputate dignissim suspendisse in. Faucibus in ornare quam viverra orci sagittis eu volutpat odio. Adipiscing elit pellentesque habitant morbi tristique senectus et netus. Diam donec adipiscing tristique risus nec feugiat in fermentum. Ac turpis egestas maecenas pharetra convallis. Pulvinar pellentesque habitant morbi tristique. Sagittis purus sit amet volutpat consequat mauris nunc. Id consectetur purus ut faucibus pulvinar elementum integer. Suspendisse sed nisi lacus sed viverra. Nunc vel risus commodo viverra. Dignissim sodales ut eu sem integer vitae justo. Egestas integer eget aliquet nibh praesent tristique magna. Quis eleifend quam adipiscing vitae proin sagittis nisl. Sed faucibus turpis in eu mi bibendum. Faucibus nisl tincidunt eget nullam non nisi est. Faucibus purus in massa tempor nec feugiat nisl pretium. Etiam tempor orci eu lobortis elementum nibh tellus. Gravida neque convallis a cras semper auctor neque vitae. Augue mauris augue neque gravida in fermentum. Praesent elementum facilisis leo vel fringilla est. Adipiscing bibendum est ultricies integer. Ut eu sem integer vitae justo eget magna fermentum. Vulputate ut pharetra sit amet aliquam id diam maecenas ultricies. Adipiscing vitae proin sagittis nisl rhoncus mattis rhoncus urna. Nibh venenatis cras sed felis eget velit aliquet. Sed viverra ipsum nunc aliquet bibendum enim facilisis gravida. Purus semper eget duis at. Morbi non arcu risus quis varius quam quisque id diam. Ipsum faucibus vitae aliquet nec ullamcorper sit. Et ligula ullamcorper malesuada proin libero nunc consequat interdum varius. Sapien et ligula ullamcorper malesuada proin libero nunc. Massa sed elementum tempus egestas sed sed risus pretium. Sapien et ligula ullamcorper malesuada proin libero nunc consequat. Ligula ullamcorper malesuada proin libero nunc consequat interdum. Non enim praesent elementum facilisis leo vel fringilla est. Nec dui nunc mattis enim ut tellus. Eget est lorem ipsum dolor sit amet consectetur adipiscing elit. Ipsum nunc aliquet bibendum enim facilisis gravida. Pellentesque elit ullamcorper dignissim cras tincidunt lobortis feugiat vivamus at. Aenean pharetra magna ac placerat vestibulum lectus mauris. Phasellus vestibulum lorem sed risus ultricies tristique nulla aliquet enim. Fringilla phasellus faucibus scelerisque eleifend donec. Odio morbi quis commodo odio aenean sed adipiscing. Tortor dignissim convallis aenean et tortor at risus. Lacus luctus accumsan tortor posuere ac ut. Nec tincidunt praesent semper feugiat nibh sed pulvinar proin. Aliquet sagittis id consectetur purus ut faucibus. Pharetra convallis posuere morbi leo urna. Turpis in eu mi bibendum neque. Purus viverra accumsan in nisl. Tincidunt tortor aliquam nulla facilisi. Nunc non blandit massa enim nec dui nunc mattis. Vel facilisis volutpat est velit egestas. Dictum varius duis at consectetur lorem donec massa sapien. Pellentesque diam volutpat commodo sed egestas egestas fringilla. Tortor posuere ac ut consequat semper. Enim blandit volutpat maecenas volutpat blandit aliquam etiam. Lectus mauris ultrices eros in cursus turpis massa tincidunt dui. Massa vitae tortor condimentum lacinia. In est ante in nibh. Nisl purus in mollis nunc sed. Sit amet risus nullam eget felis eget nunc lobortis mattis. Aliquet lectus proin nibh nisl condimentum id. Amet consectetur adipiscing elit ut aliquam purus. Mi proin sed libero enim sed faucibus turpis. Imperdiet dui accumsan sit amet nulla facilisi morbi tempus iaculis. Non nisi est sit amet facilisis magna. Vitae congue mauris rhoncus aenean vel elit scelerisque mauris pellentesque. Sit amet tellus cras adipiscing. Ultrices mi tempus imperdiet nulla malesuada pellentesque. Felis eget velit aliquet sagittis id consectetur purus. Id nibh tortor id aliquet lectus proin nibh. Lobortis feugiat vivamus at augue eget arcu. A pellentesque sit amet porttitor eget dolor. In iaculis nunc sed augue lacus viverra vitae.");
        mockMvc.perform(put("/api/operators")
                        .accept(MediaType.APPLICATION_JSON_VALUE)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(serializeInput(requestDto)))
                .andDo(result -> {
                    responseStatus(result, HttpStatus.BAD_REQUEST);
                    JSONAssert.assertEquals("""
                                    {
                                        "errors": [
                                            {
                                                "fieldName": "password",
                                                "message": "tamanho deve ser entre 4 e 250"
                                            }
                                        ]
                                    }
                                    """,
                            getContentAsString(result), true);
                });
    }

    @Test
    @DisplayName("PUT /api/operators: Esperado que ao receber um dto válido, retorne o OperatorDto")
    public void givenOperatorsWhenCreateThenExpects200() throws Exception {
        when(repository.findByEmail(eq(this.requestDto.getEmail()))).thenReturn(Optional.empty());
        when(repository.save(argThat(this::checkArgs))).thenReturn(createEntity());
        mockMvc.perform(put("/api/operators")
                        .accept(MediaType.APPLICATION_JSON_VALUE)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(serializeInput(requestDto)))
                .andDo(result -> {
                    responseStatus(result, HttpStatus.OK);
                    JSONAssert.assertEquals("""
                            {
                                "id":1,
                                "name":"John Doe",
                                "email":"john_doe@fake.com",
                                "password":"1234"
                            }
                            """, result.getResponse().getContentAsString(), true);
                });
    }

    @Test
    @DisplayName("GET /api/operators: Esperado que ao receber a chamada, retorne uma lista de OperatorDto")
    public void givenOperatorsWhenFindAllThenExpects200() throws Exception {
        when(repository.findAll()).thenReturn(List.of(createEntity(1L), createEntity(2L)));
        mockMvc.perform(get("/api/operators")
                        .accept(MediaType.APPLICATION_JSON_VALUE)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(serializeInput(requestDto)))
                .andDo(result -> {
                    responseStatus(result, HttpStatus.OK);
                    JSONAssert.assertEquals("""
                            [
                                {
                                    "id":1,
                                    "name":"John Doe",
                                    "email":"john_doe1@fake.com",
                                    "password":"1234"
                                },
                                {
                                    "id":2,
                                    "name":"John Doe",
                                    "email":"john_doe2@fake.com",
                                    "password":"1234"
                                }
                            ]
                            """, result.getResponse().getContentAsString(), true);
                });
    }

    @Test
    @DisplayName("GET /api/operators/$id: Esperado que ao receber o id de um operador existente, retorne o OperatorDto")
    public void givenOperatorsWhenFindByIdThenExpects200() throws Exception {
        when(repository.findById(eq(1L))).thenReturn(Optional.of(createEntity()));
        mockMvc.perform(get("/api/operators/1")
                        .accept(MediaType.APPLICATION_JSON_VALUE)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(serializeInput(requestDto)))
                .andDo(result -> {
                    responseStatus(result, HttpStatus.OK);
                    JSONAssert.assertEquals("""
                            {
                                "id":1,
                                "name":"John Doe",
                                "email":"john_doe@fake.com",
                                "password":"1234"
                            }
                            """, result.getResponse().getContentAsString(), true);
                });
    }

    @Test
    @DisplayName("DELETE /api/operators/$id: Esperado que ao receber o id de um operador existente, retorne o OperatorDto")
    public void givenOperatorsWhenDeleteThenExpects() throws Exception {
        when(repository.findById(eq(1L))).thenReturn(Optional.of(createEntity()));
        mockMvc.perform(delete("/api/operators/1")
                        .accept(MediaType.APPLICATION_JSON_VALUE)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(serializeInput(requestDto)))
                .andDo(result -> {
                    responseStatus(result, HttpStatus.OK);
                    JSONAssert.assertEquals("""
                            {
                                "id":1,
                                "name":"John Doe",
                                "email":"john_doe@fake.com",
                                "password":"1234"
                            }
                            """, result.getResponse().getContentAsString(), true);
                });
    }

    private OperatorRequestDto createReqDto() {
        OperatorRequestDto reqDto = new OperatorRequestDto();
        reqDto.setName("John Doe");
        reqDto.setEmail("john_doe@fake.com");
        reqDto.setPassword("1234");

        return reqDto;
    }

    private Operator createEntity() {
        Operator operator = new Operator();
        operator.setId(1L);
        operator.setName(requestDto.getName());
        operator.setEmail(requestDto.getEmail());
        operator.setPassword(requestDto.getPassword());

        return operator;
    }

    private Operator createEntity(Long id) {
        Operator operator = createEntity();
        operator.setId(id);
        operator.setEmail(String.format("john_doe%s@fake.com", id));

        return operator;
    }

    private boolean checkArgs(Operator operator) {
        return operator.getName().equals(requestDto.getName()) &&
                operator.getEmail().equals(requestDto.getEmail()) &&
                operator.getPassword().equals(requestDto.getPassword());
    }
}